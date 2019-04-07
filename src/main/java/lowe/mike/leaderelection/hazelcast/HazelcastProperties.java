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

  private int minQuorumSize = 2;

  private Map<String, String> systemProperties = new HashMap<>();

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
        && Objects.equals(systemProperties, that.systemProperties);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(minQuorumSize, systemProperties);
  }

  @Override
  public String toString() {
    return "HazelcastProperties{"
        + ", minQuorumSize=" + minQuorumSize
        + ", systemProperties=" + systemProperties
        + '}';
  }
}
