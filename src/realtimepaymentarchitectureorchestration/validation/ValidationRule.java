package realtimepaymentarchitectureorchestration.validation;

import java.util.Map;

/**
 * Functional-style validation rule that can be chained or composed.
 */
public interface ValidationRule {

    void validate(Map<String, Object> payment);
}
