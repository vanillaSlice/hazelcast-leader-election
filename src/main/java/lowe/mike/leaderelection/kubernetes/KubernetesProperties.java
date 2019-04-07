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

  private String namespace = "default";

  private String serviceName = "leader-election";

  private Duration endpointsRefresh = Duration.ofSeconds(15);

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = requireNonNull(namespace, "namespace is null");
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = requireNonNull(serviceName, "serviceName is null");
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
    return Objects.equals(namespace, that.namespace)
        && Objects.equals(serviceName, that.serviceName)
        && Objects.equals(endpointsRefresh, that.endpointsRefresh);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(namespace, serviceName, endpointsRefresh);
  }

  @Override
  public String toString() {
    return "KubernetesProperties{"
        + "namespace='" + namespace + '\''
        + ", serviceName='" + serviceName + '\''
        + ", endpointsRefresh='" + endpointsRefresh + '\''
        + '}';
  }
}
