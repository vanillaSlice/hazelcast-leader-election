package lowe.mike.leaderelection;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.hazelcast.leader.LeaderInitiator;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Readiness endpoint returns a 200 status code if this member is the leader otherwise
 * a 503 status code is returned.
 */
@Component
public class ReadyEndpoint implements Endpoint<ResponseEntity<Map<String, Object>>> {

    private final LeaderInitiator leaderInitiator;

    public ReadyEndpoint(final LeaderInitiator leaderInitiator) {
        this.leaderInitiator = requireNonNull(leaderInitiator);
    }

    @Override
    public String getId() {
        return "ready";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public ResponseEntity<Map<String, Object>> invoke() {
        if (leaderInitiator.getContext().isLeader()) {
            return new ResponseEntity<>(Collections.singletonMap("leader", true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("leader", false), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
