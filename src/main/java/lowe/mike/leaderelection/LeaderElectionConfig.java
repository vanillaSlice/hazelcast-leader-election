package lowe.mike.leaderelection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.*;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import com.hazelcast.quorum.QuorumType;
import com.hazelcast.spi.discovery.DiscoveryStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.hazelcast.lock.HazelcastLockRegistry;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.DefaultCandidate;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class LeaderElectionConfig {

    private static final Logger log = LoggerFactory.getLogger(LeaderElectionConfig.class);

    private static final String ROLE = "leader-election";

    @Bean
    public Config config(final LeaderElectionProperties leaderElectionProperties) {
        final Config config = new Config();

        config.getProperties().putAll(leaderElectionProperties.getHazelcastConfigProperties());

        final JoinConfig joinConfig = config.getNetworkConfig().getJoin();

        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(false);
        joinConfig.getAwsConfig().setEnabled(false);

        final DiscoveryStrategyFactory discoveryStrategyFactory = new HazelcastKubernetesDiscoveryStrategyFactory();

        final Map<String, Comparable> discoverStrategyProperties = new HashMap<>();
        discoverStrategyProperties.put("service-name", leaderElectionProperties.getKubernetesServiceName());
        discoverStrategyProperties.put("resolve-not-ready-addresses", true);

        final DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(discoveryStrategyFactory,
                discoverStrategyProperties);

        joinConfig.getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);

        final QuorumConfig quorumConfig = new QuorumConfig()
                .setEnabled(true)
                .setName("default-quorum")
                .setSize(leaderElectionProperties.getMinQuorumSize())
                .setType(QuorumType.READ_WRITE);

        final QuorumListenerConfig quorumListenerConfig = new QuorumListenerConfig();
        quorumListenerConfig.setImplementation(e -> log.info("Quorum present: {}", e.isPresent()));

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
    public LockRegistryLeaderInitiator initiator(final HazelcastInstance hazelcastInstance, final Candidate candidate) {
        return new LockRegistryLeaderInitiator(new HazelcastLockRegistry(hazelcastInstance), candidate);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
