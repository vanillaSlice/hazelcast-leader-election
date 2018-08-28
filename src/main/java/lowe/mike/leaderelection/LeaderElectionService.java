package lowe.mike.leaderelection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Service to determine whether the current leader should yield the leadership. The current leader
 * should yield leadership if it is outnumbered in the cluster by members with a different version
 * number. For example, if the cluster consists of three nodes and the current leader has a version
 * 1.0 and there are two nodes with version 1.1, then the current leader should yield its leadership.
 *
 * @author Mike Lowe
 */
@Service
public class LeaderElectionService {

  private static final Logger log = LoggerFactory.getLogger(LeaderElectionService.class);

  private final LockRegistryLeaderInitiator leaderInitiator;

  private final HazelcastInstance hazelcastInstance;

  private final ObjectMapper objectMapper;

  private final BuildProperties buildProperties;

  @Value("${management.port}")
  private int managementPort;

  public LeaderElectionService(final LockRegistryLeaderInitiator leaderInitiator,
      final HazelcastInstance hazelcastInstance, final ObjectMapper objectMapper,
      final BuildProperties buildProperties) {
    this.leaderInitiator = requireNonNull(leaderInitiator);
    this.hazelcastInstance = requireNonNull(hazelcastInstance);
    this.objectMapper = requireNonNull(objectMapper);
    this.buildProperties = requireNonNull(buildProperties);
  }

  @Scheduled(fixedRateString = "${leader-election.leadership-poll-millis:5000}")
  public void revokeLeadershipIfOutnumbered() throws Exception {
    // not leader so don't need to do anything
    if (!leaderInitiator.getContext().isLeader()) {
      return;
    }

    final Map<String, Integer> appVersions = getAppVersionsInCluster();

    log.info("App versions in cluster {}", appVersions);

    final String currentVersion = buildProperties.getVersion();
    final int currentVersionCount = appVersions.get(currentVersion);

    final boolean outnumbered = appVersions.entrySet().stream().anyMatch(e -> e.getValue() > currentVersionCount);

    if (outnumbered) {
      log.info("Outnumbered by other app versions in cluster");
      log.info("Destroying leader initiator");
      leaderInitiator.destroy();
      log.info("Shutting down Hazelcast instance");
      hazelcastInstance.shutdown();
    }
  }

  private Map<String, Integer> getAppVersionsInCluster() {
    final Map<String, Integer> appVersions = new HashMap<>();

    hazelcastInstance.getCluster().getMembers().forEach(member -> {
      final Optional<String> memberAppVersion = getMemberAppVersion(member);
      if (memberAppVersion.isPresent()) {
        final int memberVersionCount = appVersions.getOrDefault(memberAppVersion.get(), 0);
        appVersions.put(memberAppVersion.get(), memberVersionCount + 1);
      }
    });

    return appVersions;
  }

  @SuppressWarnings("unchecked")
  private Optional<String> getMemberAppVersion(final Member member) {
    final String infoEndpoint = "http://" + member.getSocketAddress().getHostName() + ":" + managementPort + "/info";

    try {
      final Map<String, Object> response = objectMapper.readValue(new URL(infoEndpoint), Map.class);
      final Map<String, Object> build = (Map<String, Object>) response.getOrDefault("build", Collections.emptyMap());
      final String version = build.getOrDefault("version", "").toString();
      return version.isEmpty() ? Optional.empty() : Optional.of(version);
    } catch (final Exception e) {
      log.warn("Did not get response from {}", infoEndpoint);
    }

    return Optional.empty();
  }

}
