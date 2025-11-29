package realtimepaymentarchitectureorchestration.resilience;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Simple dead-letter queue representation for failed events.
 */
public class DeadLetterQueue {

    public static class DeadLetter {

        private final String reason;
        private final Map<String, Object> payload;

        public DeadLetter(String reason, Map<String, Object> payload) {
            this.reason = reason;
            this.payload = payload;
        }

        public String getReason() {
            return reason;
        }

        public Map<String, Object> getPayload() {
            return payload;
        }
    }

    private final List<DeadLetter> entries = new ArrayList<>();

    public synchronized void add(String reason, Map<String, Object> payload) {
        entries.add(new DeadLetter(reason, payload));
    }

    public synchronized List<DeadLetter> getAll() {
        return Collections.unmodifiableList(entries);
    }
}
