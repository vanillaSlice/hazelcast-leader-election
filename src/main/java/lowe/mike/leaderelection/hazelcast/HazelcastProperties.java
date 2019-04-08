package lowe.mike.leaderelection.hazelcast;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Hazelcast properties.
 *
 * @author Mike Lowe
 */
@Configuration
@ConfigurationProperties(prefix = "hazelcast")
public class HazelcastProperties {

  private Kubernetes kubernetes = new Kubernetes();

  private int minQuorumSize = 2;

  private Map<String, String> systemProperties = new HashMap<>();

  public Kubernetes getKubernetes() {
    return kubernetes;
  }

  public void setKubernetes(Kubernetes kubernetes) {
    this.kubernetes = requireNonNull(kubernetes, "kubernetes is null");
  }

  public int getMinQuorumSize() {
    return minQuorumSize;
  }

  public void setMinQuorumSize(int minQuorumSize) {
    this.minQuorumSize = minQuorumSize;
  }

  public Map<String, String> getSystemProperties() {
    return new HashMap<>(systemProperties);
  }

  public void setSystemProperties(Map<String, String> systemProperties) {
    this.systemProperties = requireNonNull(systemProperties, "systemProperties is null");
  }

  public static class Kubernetes {

    private boolean enabled = true;

    private String namespace = "default";

    private String serviceName = "leader-election";

    public boolean isEnabled() {
      return enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

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

    @Override
    public final boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Kubernetes)) {
        return false;
      }
      Kubernetes that = (Kubernetes) o;
      return enabled == that.enabled
          && Objects.equals(namespace, that.namespace)
          && Objects.equals(serviceName, that.serviceName);
    }

    @Override
    public final int hashCode() {
      return Objects.hash(enabled, namespace, serviceName);
    }

    @Override
    public String toString() {
      return "Kubernetes{"
          + "enabled=" + enabled
          + ", namespace='" + namespace + '\''
          + ", serviceName='" + serviceName + '\''
          + '}';
    }
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HazelcastProperties)) {
      return false;
    }
    HazelcastProperties that = (HazelcastProperties) o;
    return minQuorumSize == that.minQuorumSize
        && Objects.equals(kubernetes, that.kubernetes)
        && Objects.equals(systemProperties, that.systemProperties);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(kubernetes, minQuorumSize, systemProperties);
  }

  @Override
  public String toString() {
    return "HazelcastProperties{"
        + "kubernetes=" + kubernetes
        + ", minQuorumSize=" + minQuorumSize
        + ", systemProperties=" + systemProperties
        + '}';
  }
}
