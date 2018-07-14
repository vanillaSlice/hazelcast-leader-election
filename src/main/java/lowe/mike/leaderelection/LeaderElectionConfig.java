package lowe.mike.leaderelection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.*;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import com.hazelcast.quorum.QuorumType;
import com.hazelcast.spi.discovery.DiscoveryStrategyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.hazelcast.leader.LeaderInitiator;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.DefaultCandidate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LeaderElectionConfig {

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
                .setName("leader-election")
                .setSize(4)
                .setType(QuorumType.READ_WRITE);

        final LockConfig lockConfig = new LockConfig()
                .setName("leader-election")
                .setQuorumName("leader-election");

        config.addQuorumConfig(quorumConfig)
                .addLockConfig(lockConfig);

        return config;
    }

    @Bean
    public Candidate candidate() {
        return new DefaultCandidate();
    }

    @Bean
    public LeaderInitiator initiator(final HazelcastInstance hazelcastInstance, final Candidate candidate) {
        return new LeaderInitiator(hazelcastInstance, candidate);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public LeaderElectionService leaderElectionService(final LeaderInitiator leaderInitiator,
                                                       final HazelcastInstance hazelcastInstance,
                                                       final ObjectMapper objectMapper) {
        return new LeaderElectionService(leaderInitiator, hazelcastInstance, objectMapper);
    }

}
