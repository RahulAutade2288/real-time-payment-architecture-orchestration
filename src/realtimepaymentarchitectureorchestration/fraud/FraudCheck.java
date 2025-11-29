package realtimepaymentarchitectureorchestration.fraud;

import java.util.Map;

/**
 * Abstraction for a fraud or risk check.
 */
public interface FraudCheck {

    FraudDecision evaluate(Map<String, Object> payment);
}
