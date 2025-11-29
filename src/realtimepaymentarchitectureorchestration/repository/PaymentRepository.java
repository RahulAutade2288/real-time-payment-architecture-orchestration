package realtimepaymentarchitectureorchestration.repository;

import java.util.List;
import java.util.Optional;

/**
 * Abstraction for storing and retrieving payment records.
 */
public interface PaymentRepository {

    void save(PaymentRecord record);

    Optional<PaymentRecord> findById(String id);

    List<PaymentRecord> findByStatus(PaymentStatus status);
}
