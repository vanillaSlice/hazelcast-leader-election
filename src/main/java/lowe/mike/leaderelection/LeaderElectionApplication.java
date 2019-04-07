package lowe.mike.leaderelection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point for Spring Boot.
 *
 * @author Mike Lowe
 */
@SpringBootApplication
@EnableScheduling
public class LeaderElectionApplication {

  public static void main(final String[] args) {
    SpringApplication.run(LeaderElectionApplication.class, args);
  }
}
