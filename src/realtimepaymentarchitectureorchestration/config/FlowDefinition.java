package realtimepaymentarchitectureorchestration.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A flow definition groups several logical steps into a named flow,
 * for example "domestic-rtp" or "cross-border-sample".
 */
public class FlowDefinition {

    private final String id;
    private final List<FlowStep> steps = new ArrayList<>();

    public FlowDefinition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addStep(FlowStep step) {
        this.steps.add(step);
    }

    public List<FlowStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    @Override
    public String toString() {
        return "FlowDefinition{" +
                "id='" + id + '\'' +
                ", steps=" + steps +
                '}';
    }
}
