package lowe.mike.leaderelection.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lowe.mike.leaderelection.hazelcast.HazelcastProperties;
import lowe.mike.leaderelection.hazelcast.HazelcastProperties.Kubernetes;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * {@link HazelcastProperties} unit tests.
 *
 * @author Mike Lowe
 */
public class HazelcastPropertiesTest {

  private final HazelcastProperties properties = new HazelcastProperties();

  @Test
  public void setKubernetes_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setKubernetes(null));

    assertEquals("kubernetes is null", exception.getMessage());
  }

  @Test
  public void setSystemProperties_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setSystemProperties(null));

    assertEquals("systemProperties is null", exception.getMessage());
  }

  @Test
  public void equalsAndHashCode() {
    EqualsVerifier.forClass(HazelcastProperties.class)
        .suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  /**
   * {@link Kubernetes} unit tests.
   *
   * @author Mike Lowe
   */
  @Nested
  public class KubernetesTest {

    private final Kubernetes kubernetes = new Kubernetes();

    @Test
    public void setNamespace_null_throwsNullPointerException() {
      Exception exception = assertThrows(NullPointerException.class,
          () -> kubernetes.setNamespace(null));

      assertEquals("namespace is null", exception.getMessage());
    }

    @Test
    public void setServiceName_null_throwsNullPointerException() {
      Exception exception = assertThrows(NullPointerException.class,
          () -> kubernetes.setServiceName(null));

      assertEquals("serviceName is null", exception.getMessage());
    }

    @Test
    public void equalsAndHashCode() {
      EqualsVerifier.forClass(Kubernetes.class)
          .suppress(Warning.NONFINAL_FIELDS)
          .verify();
    }
  }
}
