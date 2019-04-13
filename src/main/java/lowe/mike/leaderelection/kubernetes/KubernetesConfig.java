package lowe.mike.leaderelection.kubernetes;

import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import lowe.mike.leaderelection.election.LeaderChangeSender;
import lowe.mike.leaderelection.election.LeaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger logger = LoggerFactory.getLogger(KubernetesConfig.class);

  /**
   * Kubernetes API.
   */
  @Bean
  public CoreV1Api coreV1Api() throws IOException {
    logger.info("Creating CoreV1Api");

    io.kubernetes.client.Configuration.setDefaultApiClient(Config.defaultClient());

    return new CoreV1Api();
  }

  /**
   * Kubernetes update endpoint service.
   */
  @Bean
  public KubernetesUpdateEndpointService kubernetesUpdateEndpointService(
      CoreV1Api coreV1Api,
      KubernetesProperties properties,
      LeaderChangeSender leaderChangeSender,
      LeaderService leaderService,
      TaskScheduler taskScheduler) {
    logger.info("Creating KubernetesUpdateEndpointService");

    KubernetesUpdateEndpointService endpointService =
        new KubernetesUpdateEndpointService(coreV1Api, properties);

    leaderChangeSender.addReceiver(endpointService);

    logger.info(
        "Scheduling Kubernetes endpoint refresh at interval: {}", properties.getEndpointsRefresh());

    taskScheduler.scheduleAtFixedRate(
        () -> endpointService.update(leaderService.isLeader()), properties.getEndpointsRefresh());

    return endpointService;
  }

  /**
   * Task scheduler.
   */
  @Bean
  public TaskScheduler taskScheduler() {
    logger.info("Creating TaskScheduler");

    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.setThreadNamePrefix("KubernetesScheduler");
    scheduler.setPoolSize(1);
    scheduler.initialize();
    return scheduler;
  }
}
