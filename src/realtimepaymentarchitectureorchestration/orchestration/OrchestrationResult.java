package realtimepaymentarchitectureorchestration.orchestration;

import java.time.*;
import java.util.*;


/**
 * Represents the outcome of an orchestration run.
 */
public class OrchestrationResult {

    public enum Status {
        SUCCESS,
        FAILURE
    }

    private final Status status;
    private final List<String> messages = new ArrayList<>();
    private final Instant startedAt;
    private final Instant completedAt;

    public OrchestrationResult(Status status, Instant startedAt, Instant completedAt, Collection<String> messages) {
        this.status = Objects.requireNonNull(status, "status");
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        if (messages != null) {
            this.messages.addAll(messages);
        }
    }

    public static OrchestrationResult success(Collection<String> messages) {
        Instant now = Instant.now();
        return new OrchestrationResult(Status.SUCCESS, now, now, messages);
    }

    public static OrchestrationResult failure(Collection<String> messages) {
        Instant now = Instant.now();
        return new OrchestrationResult(Status.FAILURE, now, now, messages);
    }

    public Status getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public long getDurationMillis() {
        if (startedAt == null || completedAt == null) {
            return -1;
        }
        return Duration.between(startedAt, completedAt).toMillis();
    }

    @Override
    public String toString() {
        return "OrchestrationResult{" +
                "status=" + status +
                ", messages=" + messages +
                ", startedAt=" + startedAt +
                ", completedAt=" + completedAt +
                '}';
    }
}
