package realtimepaymentarchitectureorchestration.orchestration;

import java.time.*;
import java.util.*;


/**
 * Coordinates the high-level flow of a payment.
 */
public class PaymentOrchestrator {

    public OrchestrationResult orchestrateSimple(Map<String, Object> paymentData) {
        Objects.requireNonNull(paymentData, "paymentData");
        Instant start = Instant.now();
        List<String> messages = new ArrayList<>();

        PaymentOrchestrationContext context =
                new PaymentOrchestrationContext(UUID.randomUUID().toString());
        context.putAttribute("rawRequest", new LinkedHashMap<>(paymentData));

        try {
            context.advanceTo(OrchestrationStep.VALIDATE);
            validate(paymentData, context, messages);

            context.advanceTo(OrchestrationStep.ENRICH);
            enrich(paymentData, context, messages);

            context.advanceTo(OrchestrationStep.TRANSFORM_TO_ISO20022);
            transformToIso20022(paymentData, context, messages);

            context.advanceTo(OrchestrationStep.FRAUD_CHECK);
            fraudCheck(paymentData, context, messages);

            context.advanceTo(OrchestrationStep.ROUTE);
            route(paymentData, context, messages);

            context.advanceTo(OrchestrationStep.POST);
            post(paymentData, context, messages);

            context.advanceTo(OrchestrationStep.NOTIFY);
            notifyParties(paymentData, context, messages);

            context.advanceTo(OrchestrationStep.COMPLETE);
            messages.add("Payment completed successfully with internalId=" + context.getInternalId());
            messages.addAll(context.getAuditTrail());
            Instant end = Instant.now();
            return new OrchestrationResult(OrchestrationResult.Status.SUCCESS, start, end, messages);
        } catch (Exception ex) {
            context.advanceTo(OrchestrationStep.ERROR);
            messages.add("Payment orchestration failed: " + ex.getMessage());
            messages.addAll(context.getAuditTrail());
            Instant end = Instant.now();
            return new OrchestrationResult(OrchestrationResult.Status.FAILURE, start, end, messages);
        }
    }

    private void validate(Map<String, Object> paymentData,
                          PaymentOrchestrationContext context,
                          List<String> messages) {
        String debtor = (String) paymentData.get("debtorAccount");
        String creditor = (String) paymentData.get("creditorAccount");
        String amountStr = (String) paymentData.get("amount");

        if (debtor == null || debtor.isBlank()) {
            throw new IllegalArgumentException("Debtor account is required");
        }
        if (creditor == null || creditor.isBlank()) {
            throw new IllegalArgumentException("Creditor account is required");
        }
        if (amountStr == null || amountStr.isBlank()) {
            throw new IllegalArgumentException("Amount is required");
        }
        try {
            Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be numeric", e);
        }

        context.putAttribute("validated", Boolean.TRUE);
        messages.add("Validation succeeded");
    }

    private void enrich(Map<String, Object> paymentData,
                        PaymentOrchestrationContext context,
                        List<String> messages) {
        context.putAttribute("channel", "CLI_DEMO");
        context.putAttribute("receivedAt", Instant.now());
        messages.add("Enrichment completed");
    }

    private void transformToIso20022(Map<String, Object> paymentData,
                                     PaymentOrchestrationContext context,
                                     List<String> messages) {
        Map<String, Object> iso = new LinkedHashMap<>();
        iso.put("msgId", "MSG-" + context.getInternalId());
        iso.put("debtorAccount", paymentData.get("debtorAccount"));
        iso.put("creditorAccount", paymentData.get("creditorAccount"));
        iso.put("amount", paymentData.get("amount"));
        iso.put("currency", paymentData.getOrDefault("currency", "USD"));
        context.putAttribute("iso20022Message", iso);
        messages.add("Transformed into ISO 20022 inspired structure");
    }

    private void fraudCheck(Map<String, Object> paymentData,
                            PaymentOrchestrationContext context,
                            List<String> messages) {
        double amount = Double.parseDouble((String) paymentData.get("amount"));
        if (amount > 50000.0) {
            messages.add("Fraud check result: REVIEW (high amount)");
            context.putAttribute("fraudDecision", "REVIEW");
        } else {
            messages.add("Fraud check result: APPROVE");
            context.putAttribute("fraudDecision", "APPROVE");
        }
    }

    private void route(Map<String, Object> paymentData,
                       PaymentOrchestrationContext context,
                       List<String> messages) {
        String currency = (String) paymentData.getOrDefault("currency", "USD");
        String route;
        if ("USD".equalsIgnoreCase(currency)) {
            route = "INTERNAL_RT";
        } else {
            route = "CROSS_BORDER";
        }
        context.putAttribute("route", route);
        messages.add("Routing decided: " + route);
    }

    private void post(Map<String, Object> paymentData,
                      PaymentOrchestrationContext context,
                      List<String> messages) {
        String ledgerId = "LEDGER-" + context.getInternalId();
        context.putAttribute("ledgerId", ledgerId);
        messages.add("Posted to simulated ledger with id=" + ledgerId);
    }

    private void notifyParties(Map<String, Object> paymentData,
                               PaymentOrchestrationContext context,
                               List<String> messages) {
        messages.add("Notifications sent to debtor and creditor (simulated)");
    }
}
