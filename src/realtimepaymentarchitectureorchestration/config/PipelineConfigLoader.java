package realtimepaymentarchitectureorchestration.config;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Very small, in-memory configuration loader for flow definitions.
 * In a real deployment this information might come from YAML, JSON,
 * a database, or a configuration service. The goal here is to show
 * how flows can be driven by configuration instead of hard-coded logic.
 */
public class PipelineConfigLoader {

    private final Map<String, FlowDefinition> flows = new LinkedHashMap<>();

    public PipelineConfigLoader() {
        // Pre-register a few example flows.
        register("domestic-rtp", Arrays.asList(
                "VALIDATE", "ENRICH", "TRANSFORM_TO_ISO20022",
                "FRAUD_CHECK", "ROUTE", "POST", "NOTIFY"
        ));
        register("high-value-review", Arrays.asList(
                "VALIDATE", "ENRICH", "TRANSFORM_TO_ISO20022",
                "FRAUD_CHECK", "ROUTE", "POST"
        ));
        register("cross-border-sample", Arrays.asList(
                "VALIDATE", "ENRICH", "TRANSFORM_TO_ISO20022",
                "FRAUD_CHECK", "ROUTE", "POST", "NOTIFY"
        ));
    }

    private void register(String id, List<String> stepNames) {
        FlowDefinition def = new FlowDefinition(id);
        for (String name : stepNames) {
            def.addStep(new FlowStep(name));
        }
        flows.put(id, def);
    }

    public FlowDefinition getFlow(String id) {
        return flows.get(id);
    }

    public Map<String, FlowDefinition> getAllFlows() {
        return java.util.Collections.unmodifiableMap(flows);
    }
}
