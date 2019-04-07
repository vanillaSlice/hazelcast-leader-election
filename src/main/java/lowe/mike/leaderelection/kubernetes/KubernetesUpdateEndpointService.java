package lowe.mike.leaderelection.kubernetes;

import lowe.mike.leaderelection.election.LeaderChangeReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service that updates the Kubernetes service endpoint to route traffic to only this instance of
 * the application.
 *
 * @author Mike Lowe
 */
public class KubernetesUpdateEndpointService implements LeaderChangeReceiver {

  private static final Logger logger =
      LoggerFactory.getLogger(KubernetesUpdateEndpointService.class);

  @Override
  public void update(boolean isLeader) {
    if (!isLeader) {
      return;
    }

    logger.info("Updating Kubernetes endpoint");
  }
}
