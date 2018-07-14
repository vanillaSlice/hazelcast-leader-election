package lowe.mike.leaderelection;

import org.springframework.integration.hazelcast.leader.LeaderInitiator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@RestController
public class LeaderElectionController {

    private final LeaderInitiator leaderInitiator;

    public LeaderElectionController(final LeaderInitiator leaderInitiator) {
        this.leaderInitiator = requireNonNull(leaderInitiator);
    }

    @GetMapping("/leader")
    public Map<String, Object> leader() {
        return Collections.singletonMap("leader", leaderInitiator.getContext().isLeader());
    }

}
