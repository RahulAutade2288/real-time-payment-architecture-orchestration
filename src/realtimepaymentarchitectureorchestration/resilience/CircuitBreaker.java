package realtimepaymentarchitectureorchestration.resilience;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Very small circuit breaker implementation to illustrate the pattern.
 */
public class CircuitBreaker {

    public enum State {
        CLOSED,
        OPEN,
        HALF_OPEN
    }

    private final int failureThreshold;
    private final Duration openDuration;
    private final AtomicInteger failureCount = new AtomicInteger(0);
    private volatile State state = State.CLOSED;
    private volatile Instant openedAt;

    public CircuitBreaker(int failureThreshold, Duration openDuration) {
        this.failureThreshold = failureThreshold;
        this.openDuration = openDuration;
    }

    public <T> T execute(Supplier<T> supplier) {
        if (state == State.OPEN && Instant.now().isBefore(openedAt.plus(openDuration))) {
            throw new IllegalStateException("Circuit breaker is OPEN");
        }
        if (state == State.OPEN) {
            state = State.HALF_OPEN;
        }
        try {
            T result = supplier.get();
            onSuccess();
            return result;
        } catch (RuntimeException ex) {
            onFailure();
            throw ex;
        }
    }

    private void onSuccess() {
        failureCount.set(0);
        state = State.CLOSED;
    }

    private void onFailure() {
        int failures = failureCount.incrementAndGet();
        if (failures >= failureThreshold) {
            state = State.OPEN;
            openedAt = Instant.now();
        }
    }

    public State getState() {
        return state;
    }
}
