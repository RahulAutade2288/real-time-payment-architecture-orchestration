package realtimepaymentarchitectureorchestration.repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Thread-safe in-memory repository implementation suitable for demos and tests.
 */
public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<String, PaymentRecord> store = new LinkedHashMap<>();

    @Override
    public synchronized void save(PaymentRecord record) {
        store.put(record.getId(), record);
    }

    @Override
    public synchronized Optional<PaymentRecord> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public synchronized List<PaymentRecord> findByStatus(PaymentStatus status) {
        List<PaymentRecord> result = new ArrayList<>();
        for (PaymentRecord record : store.values()) {
            if (record.getStatus() == status) {
                result.add(record);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public synchronized PaymentRecord createNew(String id, Map<String, Object> payload) {
        PaymentRecord record = new PaymentRecord(id, PaymentStatus.RECEIVED,
                OffsetDateTime.now(), OffsetDateTime.now(), payload);
        save(record);
        return record;
    }
}
