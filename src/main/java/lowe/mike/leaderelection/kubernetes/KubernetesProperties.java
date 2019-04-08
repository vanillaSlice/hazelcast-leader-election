package lowe.mike.leaderelection.kubernetes;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Kubernetes properties.
 *
 * @author Mike Lowe
 */
@Configuration
@ConfigurationProperties(prefix = "kubernetes")
public class KubernetesProperties {

  private Duration endpointsRefresh = Duration.ofSeconds(15);

  public Duration getEndpointsRefresh() {
    return endpointsRefresh;
  }

  public void setEndpointsRefresh(Duration endpointsRefresh) {
    this.endpointsRefresh = requireNonNull(endpointsRefresh, "endpointsRefresh is null");
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KubernetesProperties)) {
      return false;
    }
    KubernetesProperties that = (KubernetesProperties) o;
    return Objects.equals(endpointsRefresh, that.endpointsRefresh);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(endpointsRefresh);
  }

  @Override
  public String toString() {
    return "KubernetesProperties{"
        + "endpointsRefresh='" + endpointsRefresh + '\''
        + '}';
  }
}
