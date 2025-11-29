package realtimepaymentarchitectureorchestration.observability;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Helper for structured logging of payment-related events.
 */
public class PaymentLogger {

    public void log(String correlationId, String eventType, Map<String, Object> payload) {
        System.out.println("[" + OffsetDateTime.now() + "] " + eventType +
                " correlationId=" + correlationId);
        if (payload != null) {
            for (Map.Entry<String, Object> entry : payload.entrySet()) {
                System.out.println("  " + entry.getKey() + " = " + entry.getValue());
            }
        }
    }
}
