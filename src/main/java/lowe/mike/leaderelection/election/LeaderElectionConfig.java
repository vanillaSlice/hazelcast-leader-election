package lowe.mike.leaderelection.election;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.support.locks.LockRegistry;

/**
 * Leader election config.
 *
 * @author Mike Lowe
 */
@Configuration
public class LeaderElectionConfig {

  @Bean
  public Candidate candidate(@Value("${spring.application.name}") String applicationName) {
    return new LeaderElectionCandidate(applicationName);
  }

  @Bean
  public LockRegistryLeaderInitiator leaderInitiator(LockRegistry lockRegistry,
      Candidate candidate) {
    return new LockRegistryLeaderInitiator(lockRegistry, candidate);
  }

  @Bean
  public LeaderService leaderService(LockRegistryLeaderInitiator leaderInitiator) {
    return new LockRegistryLeaderService(leaderInitiator);
  }
}
