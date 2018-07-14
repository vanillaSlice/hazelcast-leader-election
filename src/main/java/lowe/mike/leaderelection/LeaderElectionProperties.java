package lowe.mike.leaderelection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@Configuration
@ConfigurationProperties(prefix = "leader-election")
public class LeaderElectionProperties {

    private Map<String, String> hazelcastConfigProperties = new HashMap<>();

    private String kubernetesServiceName = "leader-election";

    public Map<String, String> getHazelcastConfigProperties() {
        return hazelcastConfigProperties;
    }

    public void setHazelcastConfigProperties(final Map<String, String> hazelcastConfigProperties) {
        this.hazelcastConfigProperties = requireNonNull(hazelcastConfigProperties);
    }

    public String getKubernetesServiceName() {
        return kubernetesServiceName;
    }

    public void setKubernetesServiceName(final String kubernetesServiceName) {
        this.kubernetesServiceName = requireNonNull(kubernetesServiceName);
    }

}
