package lowe.mike.leaderelection;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Map;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Application controller.
 *
 * @author Mike Lowe
 */
@RestController
public class LeaderElectionController {

  private final LockRegistryLeaderInitiator leaderInitiator;

  public LeaderElectionController(final LockRegistryLeaderInitiator leaderInitiator) {
    this.leaderInitiator = requireNonNull(leaderInitiator);
  }

  @GetMapping("/leader")
  public Map<String, Object> leader() {
    return Collections.singletonMap("leader", leaderInitiator.getContext().isLeader());
  }
}
