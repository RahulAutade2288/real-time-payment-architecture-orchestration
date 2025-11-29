package realtimepaymentarchitectureorchestration.observability;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Very small in-memory metrics registry.
 */
public class MetricsRegistry {

    private final Map<String, AtomicLong> counters = new LinkedHashMap<>();

    public void increment(String name) {
        counters.computeIfAbsent(name, n -> new AtomicLong()).incrementAndGet();
    }

    public long get(String name) {
        AtomicLong counter = counters.get(name);
        return counter != null ? counter.get() : 0L;
    }

    public Map<String, Long> snapshot() {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Map.Entry<String, AtomicLong> entry : counters.entrySet()) {
            result.put(entry.getKey(), entry.getValue().get());
        }
        return Collections.unmodifiableMap(result);
    }
}
