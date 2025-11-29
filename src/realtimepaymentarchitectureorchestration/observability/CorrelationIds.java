package realtimepaymentarchitectureorchestration.observability;

import java.util.UUID;

/**
 * Utility for creating and propagating correlation identifiers.
 */
public final class CorrelationIds {

    private CorrelationIds() {}

    public static String newCorrelationId() {
        return "CORR-" + UUID.randomUUID();
    }
}
