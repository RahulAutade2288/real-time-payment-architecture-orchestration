package realtimepaymentarchitectureorchestration.channel;

import java.util.Map;

/**
 * Abstraction for any channel that can submit a payment into the system.
 * Implementations may represent REST APIs, message queues, file drops, etc.
 */
public interface PaymentChannelAdapter {

    /**
     * Submit a payment request represented as a map of key-value pairs.
     * Typical keys would include debtorAccount, creditorAccount, amount, currency, and metadata.
     *
     * Implementations are expected to perform lightweight checks and then delegate
     * to the orchestration layer.
     */
    void submitPayment(Map<String, Object> paymentRequest);
}
