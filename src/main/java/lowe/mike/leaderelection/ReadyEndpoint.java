package lowe.mike.leaderelection;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Readiness endpoint returns a 200 status code if this member is the leader otherwise a 503 status
 * code is returned.
 *
 * @author Mike Lowe
 */
@RestController
public class ReadyEndpoint {

  private final LockRegistryLeaderInitiator leaderInitiator;

  public ReadyEndpoint(final LockRegistryLeaderInitiator leaderInitiator) {
    this.leaderInitiator = requireNonNull(leaderInitiator);
  }

  /**
   * Invoke endpoint.
   */
  @GetMapping("/ready")
  public ResponseEntity<Map<String, Object>> ready() {
    if (leaderInitiator.getContext().isLeader()) {
      return new ResponseEntity<>(Collections.singletonMap("leader", true), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(Collections.singletonMap("leader", false),
          HttpStatus.SERVICE_UNAVAILABLE);
    }
  }
}
