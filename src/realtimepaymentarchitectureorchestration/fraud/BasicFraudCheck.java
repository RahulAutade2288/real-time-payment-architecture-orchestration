package realtimepaymentarchitectureorchestration.fraud;

import java.util.Map;
import java.math.BigDecimal;

/**
 * Basic fraud check that flags high amounts for review and rejects obviously invalid data.
 */
public class BasicFraudCheck implements FraudCheck {

    private final BigDecimal reviewThreshold;
    private final BigDecimal rejectThreshold;

    public BasicFraudCheck(BigDecimal reviewThreshold, BigDecimal rejectThreshold) {
        this.reviewThreshold = reviewThreshold;
        this.rejectThreshold = rejectThreshold;
    }

    @Override
    public FraudDecision evaluate(Map<String, Object> payment) {
        Object amountObj = payment.get("amount");
        if (!(amountObj instanceof String)) {
            return new FraudDecision(FraudDecision.Outcome.REJECT, "Amount missing or not a string");
        }
        BigDecimal amount;
        try {
            amount = new BigDecimal((String) amountObj);
        } catch (NumberFormatException ex) {
            return new FraudDecision(FraudDecision.Outcome.REJECT, "Amount is not numeric");
        }

        if (amount.compareTo(rejectThreshold) > 0) {
            return new FraudDecision(FraudDecision.Outcome.REJECT, "Amount exceeds reject threshold");
        }
        if (amount.compareTo(reviewThreshold) > 0) {
            return new FraudDecision(FraudDecision.Outcome.REVIEW, "Amount exceeds review threshold");
        }
        return new FraudDecision(FraudDecision.Outcome.APPROVE, "Amount within normal range");
    }
}
