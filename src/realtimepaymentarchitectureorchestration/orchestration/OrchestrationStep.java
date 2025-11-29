package realtimepaymentarchitectureorchestration.orchestration;


/**
 * Canonical steps in the real-time payment lifecycle.
 */
public enum OrchestrationStep {
    RECEIVE_REQUEST,
    AUTHENTICATE,
    VALIDATE,
    ENRICH,
    TRANSFORM_TO_ISO20022,
    FRAUD_CHECK,
    ROUTE,
    POST,
    NOTIFY,
    COMPLETE,
    ERROR;

    public boolean isTerminal() {
        return this == COMPLETE || this == ERROR;
    }
}
