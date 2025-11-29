package realtimepaymentarchitectureorchestration.orchestration;

import java.time.*;
import java.util.*;


/**
 * AsyncTaskExecutor is a supporting type in the orchestration layer.
 */
public class AsyncTaskExecutor {

    private final Map<String, Object> metadata = new LinkedHashMap<>();

    public AsyncTaskExecutor() {
        metadata.put("createdAt", Instant.now());
        metadata.put("createdBy", "AsyncTaskExecutor");
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
        System.out.println("AsyncTaskExecutor state:");
        for (Map.Entry<String, Object> entry : metadata.entrySet()) {
            System.out.println("  " + entry.getKey() + " = " + entry.getValue());
        }
    }
}
