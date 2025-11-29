package realtimepaymentarchitectureorchestration.orchestration;

import java.time.*;
import java.util.*;


/**
 * Holds the mutable state of a payment while it is being orchestrated.
 */
public class PaymentOrchestrationContext {

    private final String internalId;
    private OrchestrationStep currentStep;
    private final Map<String, Object> attributes = new LinkedHashMap<>();
    private final List<String> auditTrail = new ArrayList<>();

    public PaymentOrchestrationContext(String internalId) {
        this.internalId = Objects.requireNonNull(internalId, "internalId");
        this.currentStep = OrchestrationStep.RECEIVE_REQUEST;
        addAudit("Context created");
    }

    public String getInternalId() {
        return internalId;
    }

    public OrchestrationStep getCurrentStep() {
        return currentStep;
    }

    public void advanceTo(OrchestrationStep nextStep) {
        Objects.requireNonNull(nextStep, "nextStep");
        addAudit("Advancing from " + currentStep + " to " + nextStep);
        this.currentStep = nextStep;
    }

    public void putAttribute(String key, Object value) {
        attributes.put(key, value);
        addAudit("Attribute set: " + key + "=" + value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, Class<T> type) {
        Object value = attributes.get(key);
        if (value == null) {
            return null;
        }
        if (!type.isInstance(value)) {
            throw new IllegalStateException("Attribute " + key + " is not of type " + type.getName());
        }
        return (T) value;
    }

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public List<String> getAuditTrail() {
        return Collections.unmodifiableList(auditTrail);
    }

    public void addAudit(String message) {
        auditTrail.add(OffsetDateTime.now() + " - " + message);
    }
}
