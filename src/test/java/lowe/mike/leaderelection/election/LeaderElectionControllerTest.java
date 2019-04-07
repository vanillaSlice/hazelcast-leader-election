package lowe.mike.leaderelection.election;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

/**
 * {@link LeaderElectionController} unit tests.
 *
 * @author Mike Lowe
 */
public class LeaderElectionControllerTest {

  private final LeaderService leaderService = mock(LeaderService.class);

  private final LeaderElectionController leaderElectionController =
      new LeaderElectionController(leaderService);

  @Test
  public void constructor_nullLeaderService_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> new LeaderElectionController(null));

    assertEquals("leaderService is null", exception.getMessage());
  }

  @Test
  public void leader_returnsIfLeaderInMap() {
    when(leaderService.isLeader()).thenReturn(true, false);

    assertTrue((boolean) leaderElectionController.leader().get("leader"));
    assertFalse((boolean) leaderElectionController.leader().get("leader"));
  }
}
