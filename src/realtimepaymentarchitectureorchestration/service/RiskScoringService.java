package realtimepaymentarchitectureorchestration.service;

import java.math.*;
import java.time.*;
import java.util.*;


/**
 * RiskScoringService represents a domain service in the reference architecture.
 */
public class RiskScoringService {

    private final Random random = new Random();

    public String generateCorrelationId() {
        return "CORR-" + UUID.randomUUID();
    }

    public boolean simulateBooleanDecision(double probabilityTrue) {
        return random.nextDouble() < probabilityTrue;
    }

    public BigDecimal calculateFee(BigDecimal amount, BigDecimal percentage) {
        if (amount == null || percentage == null) {
            return BigDecimal.ZERO;
        }
        return amount.multiply(percentage).setScale(2, RoundingMode.HALF_UP);
    }

    public void logEvent(String eventType, Map<String, Object> payload) {
        System.out.println("[" + OffsetDateTime.now() + "] " + eventType);
        if (payload != null) {
            for (Map.Entry<String, Object> entry : payload.entrySet()) {
                System.out.println("  " + entry.getKey() + " = " + entry.getValue());
            }
        }
    }
}
