package lowe.mike.leaderelection.election;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Leader election controller.
 *
 * @author Mike Lowe
 */
@RestController
public class LeaderElectionController {

  private final LeaderService leaderService;

  public LeaderElectionController(LeaderService leaderService) {
    this.leaderService = requireNonNull(leaderService, "leaderService is null");
  }

  @GetMapping("/leader")
  public Map<String, Object> leader() {
    return Collections.singletonMap("leader", leaderService.isLeader());
  }
}
