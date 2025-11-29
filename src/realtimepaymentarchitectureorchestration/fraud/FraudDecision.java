package realtimepaymentarchitectureorchestration.fraud;

/**
 * Simple outcome model for a fraud check.
 */
public class FraudDecision {

    public enum Outcome {
        APPROVE,
        REVIEW,
        REJECT
    }

    private final Outcome outcome;
    private final String reason;

    public FraudDecision(Outcome outcome, String reason) {
        this.outcome = outcome;
        this.reason = reason;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "FraudDecision{" +
                "outcome=" + outcome +
                ", reason='" + reason + '\'' +
                '}';
    }
}
