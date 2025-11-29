package realtimepaymentarchitectureorchestration.orchestration;

import java.time.*;
import java.util.*;


/**
 * RetryPolicy is a supporting type in the orchestration layer.
 */
public class RetryPolicy {

    private final Map<String, Object> metadata = new LinkedHashMap<>();

    public RetryPolicy() {
        metadata.put("createdAt", Instant.now());
        metadata.put("createdBy", "RetryPolicy");
    }

    public void put(String key, Object value) {
        metadata.put(key, value);
    }

    public Optional<Object> get(String key) {
        return Optional.ofNullable(metadata.get(key));
    }

    public Map<String, Object> getAll() {
        return Collections.unmodifiableMap(metadata);
    }

    public void logState() {
        System.out.println("RetryPolicy state:");
        for (Map.Entry<String, Object> entry : metadata.entrySet()) {
            System.out.println("  " + entry.getKey() + " = " + entry.getValue());
        }
    }
}
