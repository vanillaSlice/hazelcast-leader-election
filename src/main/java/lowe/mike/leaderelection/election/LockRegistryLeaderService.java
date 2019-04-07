package lowe.mike.leaderelection.election;

import static java.util.Objects.requireNonNull;

import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;

/**
 * Service which can be called to find out if this instance is the leader.
 *
 * @author Mike Lowe
 */
public class LockRegistryLeaderService implements LeaderService {

  private final LockRegistryLeaderInitiator leaderInitiator;

  public LockRegistryLeaderService(LockRegistryLeaderInitiator leaderInitiator) {
    this.leaderInitiator = requireNonNull(leaderInitiator, "leaderInitiator is null");
  }

  @Override
  public boolean isLeader() {
    return leaderInitiator.getContext().isLeader();
  }
}
