package lowe.mike.leaderelection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.LockConfig;
import com.hazelcast.config.QuorumConfig;
import com.hazelcast.config.QuorumListenerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.quorum.QuorumType;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.hazelcast.lock.HazelcastLockRegistry;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.DefaultCandidate;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;

/**
 * Application config.
 *
 * @author Mike Lowe
 */
@Configuration
public class LeaderElectionConfig {

  private static final Logger logger = LoggerFactory.getLogger(LeaderElectionConfig.class);

  private static final String ROLE = "leader-election";

  /**
   * Hazelcast config.
   */
  @Bean
  public Config config(final LeaderElectionProperties leaderElectionProperties) {
    final Config config = new Config();

    config.getProperties().putAll(leaderElectionProperties.getHazelcastConfigProperties());

    final JoinConfig joinConfig = config.getNetworkConfig().getJoin();

    joinConfig.getMulticastConfig().setEnabled(false);
    joinConfig.getKubernetesConfig().setEnabled(true)
        .setProperty("namespace", "default")
        .setProperty("service-name", leaderElectionProperties.getKubernetesServiceName())
        .setProperty("resolve-not-ready-addresses", "true");

    final QuorumConfig quorumConfig = new QuorumConfig()
        .setEnabled(true)
        .setName("default-quorum")
        .setSize(leaderElectionProperties.getMinQuorumSize())
        .setType(QuorumType.READ_WRITE);

    final QuorumListenerConfig quorumListenerConfig = new QuorumListenerConfig();
    quorumListenerConfig.setImplementation(e -> logger.info("Quorum present: {}", e.isPresent()));

    quorumConfig.addListenerConfig(quorumListenerConfig);

    final LockConfig lockConfig = new LockConfig()
        .setName(ROLE)
        .setQuorumName("default-quorum");

    config.addQuorumConfig(quorumConfig)
        .addLockConfig(lockConfig);

    return config;
  }

  @Bean
  public Candidate candidate() {
    return new DefaultCandidate(UUID.randomUUID().toString(), ROLE);
  }

  @Bean
  public LockRegistryLeaderInitiator initiator(final HazelcastInstance hazelcastInstance,
      final Candidate candidate) {
    return new LockRegistryLeaderInitiator(new HazelcastLockRegistry(hazelcastInstance), candidate);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
