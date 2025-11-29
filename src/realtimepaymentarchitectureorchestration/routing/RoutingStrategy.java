package realtimepaymentarchitectureorchestration.routing;

import java.util.Map;

/**
 * Strategy used by PaymentRouter to decide where to send a payment.
 */
public interface RoutingStrategy {

    String determineRoute(Map<String, Object> payment);
}
