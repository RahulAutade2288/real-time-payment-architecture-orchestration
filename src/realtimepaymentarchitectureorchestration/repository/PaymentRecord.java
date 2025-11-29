package realtimepaymentarchitectureorchestration.repository;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Lightweight record of a payment for persistence purposes.
 */
public class PaymentRecord {

    private final String id;
    private PaymentStatus status;
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private final Map<String, Object> payload;

    public PaymentRecord(String id, PaymentStatus status, OffsetDateTime createdAt,
                         OffsetDateTime updatedAt, Map<String, Object> payload) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
        this.updatedAt = OffsetDateTime.now();
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }
}
