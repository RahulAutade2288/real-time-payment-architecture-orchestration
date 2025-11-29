package realtimepaymentarchitectureorchestration.config;

/**
 * Represents a single logical step identifier in a flow definition.
 * For simplicity this is just a small value object; the mapping to
 * concrete orchestration steps happens in the orchestration layer.
 */
public class FlowStep {

    private final String name;

    public FlowStep(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FlowStep{" +
                "name='" + name + '\'' +
                '}';
    }
}
