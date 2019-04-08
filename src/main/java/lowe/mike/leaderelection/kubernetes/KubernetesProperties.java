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

  private boolean enabled = true;

  private Duration endpointsRefresh = Duration.ofSeconds(15);

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

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
    return enabled == that.enabled
        && Objects.equals(endpointsRefresh, that.endpointsRefresh);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(enabled, endpointsRefresh);
  }

  @Override
  public String toString() {
    return "KubernetesProperties{"
        + "enabled=" + enabled
        + ", endpointsRefresh=" + endpointsRefresh
        + '}';
  }
}
