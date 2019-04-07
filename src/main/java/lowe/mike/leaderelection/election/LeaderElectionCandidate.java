package lowe.mike.leaderelection.election;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.DefaultCandidate;

/**
 * Leader election candidate that notifies {@link LeaderChangeReceiver}s when leadership changes.
 *
 * @author Mike Lowe
 */
public class LeaderElectionCandidate extends DefaultCandidate implements LeaderChangeSender {

  private final List<LeaderChangeReceiver> receivers = new ArrayList<>();

  public LeaderElectionCandidate(String role) {
    super(null, role);
  }

  @Override
  public void addReceiver(LeaderChangeReceiver receiver) {
    requireNonNull(receiver, "receiver is null");
    synchronized (receivers) {
      receivers.add(receiver);
    }
  }

  @Override
  public void onGranted(Context ctx) {
    super.onGranted(ctx);
    synchronized (receivers) {
      receivers.forEach(receiver -> receiver.update(true));
    }
  }

  @Override
  public void onRevoked(Context ctx) {
    super.onRevoked(ctx);
    synchronized (receivers) {
      receivers.forEach(receiver -> receiver.update(false));
    }
  }
}
