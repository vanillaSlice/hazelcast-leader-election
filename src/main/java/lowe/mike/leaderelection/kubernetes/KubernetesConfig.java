package lowe.mike.leaderelection.kubernetes;

import lowe.mike.leaderelection.election.LeaderChangeSender;
import lowe.mike.leaderelection.election.LeaderService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Kubernetes config.
 *
 * @author Mike Lowe
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(
    prefix = "kubernetes",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true
)
public class KubernetesConfig {

  /**
   * Kubernetes update endpoint service.
   */
  @Bean
  public KubernetesUpdateEndpointService kubernetesUpdateEndpointService(
      KubernetesProperties properties,
      LeaderChangeSender leaderChangeSender,
      LeaderService leaderService,
      TaskScheduler taskScheduler) {
    KubernetesUpdateEndpointService endpointService = new KubernetesUpdateEndpointService();

    leaderChangeSender.addReceiver(endpointService);

    taskScheduler.scheduleAtFixedRate(
        () -> endpointService.update(leaderService.isLeader()), properties.getEndpointsRefresh());

    return endpointService;
  }

  /**
   * Task scheduler.
   */
  @Bean
  public TaskScheduler taskScheduler() {
    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.setThreadNamePrefix("KubernetesScheduler");
    scheduler.setPoolSize(1);
    scheduler.initialize();
    return scheduler;
  }
}
