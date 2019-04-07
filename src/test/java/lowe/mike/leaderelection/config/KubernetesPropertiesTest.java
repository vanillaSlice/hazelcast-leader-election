package lowe.mike.leaderelection.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lowe.mike.leaderelection.kubernetes.KubernetesProperties;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

/**
 * {@link KubernetesProperties} unit tests.
 *
 * @author Mike Lowe
 */
public class KubernetesPropertiesTest {

  private final KubernetesProperties properties = new KubernetesProperties();

  @Test
  public void setNamespace_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setNamespace(null));

    assertEquals("namespace is null", exception.getMessage());
  }

  @Test
  public void setServiceName_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setServiceName(null));

    assertEquals("serviceName is null", exception.getMessage());
  }

  @Test
  public void setEndpointsRefresh_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setEndpointsRefresh(null));

    assertEquals("endpointsRefresh is null", exception.getMessage());
  }

  @Test
  public void equalsAndHashCode() {
    EqualsVerifier.forClass(KubernetesProperties.class)
        .suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }
}
