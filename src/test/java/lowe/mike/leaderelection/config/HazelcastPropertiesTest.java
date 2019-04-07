package lowe.mike.leaderelection.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lowe.mike.leaderelection.hazelcast.HazelcastProperties;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

/**
 * {@link HazelcastProperties} unit tests.
 *
 * @author Mike Lowe
 */
public class HazelcastPropertiesTest {

  private final HazelcastProperties properties = new HazelcastProperties();

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
}
