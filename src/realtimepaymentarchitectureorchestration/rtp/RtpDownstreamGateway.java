
package rtp;

/**
 * RtpDownstreamGateway
 *
 * This class is part of the RTP rail blueprint. It contains multiple
 * rich processing flows used for demonstrations, testing, and architectural
 * reference. Each method simulates realistic validation, routing, downstream
 * interaction, and audit logging as you would expect in a production-grade
 * digital payments platform.
 */
public class RtpDownstreamGateway {


public void validateAndRoutePayment(PaymentContext context) {
    java.util.Map<String, Object> attributes = context.getAttributes();
    java.util.List<String> auditTrail = context.getAuditTrail();
    auditTrail.add("Starting RTP flow: validateAndRoutePayment");

    String txId = context.getTransactionId();
    if (txId == null || txId.isBlank()) {
        txId = java.util.UUID.randomUUID().toString();
        context.setTransactionId(txId);
        auditTrail.add("Generated new transaction id: " + txId);
    }

    String debtor = (String) attributes.getOrDefault("debtorAccount", "UNKNOWN");
    String creditor = (String) attributes.getOrDefault("creditorAccount", "UNKNOWN");
    String currency = String.valueOf(attributes.getOrDefault("currency", "USD"));
    String amountStr = String.valueOf(attributes.getOrDefault("amount", "0.00"));

    if (debtor.equals("UNKNOWN") || creditor.equals("UNKNOWN")) {
        context.addWarning("MISSING_PARTY", debtor + "->" + creditor);
        auditTrail.add("Missing party information for tx " + txId);
    }

    java.math.BigDecimal amount;
    try {
        amount = new java.math.BigDecimal(amountStr);
    } catch (NumberFormatException ex) {
        context.addError("INVALID_AMOUNT_FORMAT", amountStr);
        auditTrail.add("Invalid amount format for tx " + txId + ": " + amountStr);
        return;
    }

    if (amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
        context.addError("NON_POSITIVE_AMOUNT", amount.toPlainString());
        auditTrail.add("Non-positive amount detected for tx " + txId);
    }

    java.util.Set<String> supported = java.util.Set.of("USD","EUR","GBP","INR");
    if (!supported.contains(currency)) {
        context.addWarning("UNSUPPORTED_CURRENCY", currency);
        auditTrail.add("Unsupported currency " + currency + " for tx " + txId);
    }

    for (int i = 0; i < 5; i++) {
        String key = "meta_" + i;
        Object value = attributes.get(key);
        if (value != null) {
            auditTrail.add("Meta attribute " + key + " = " + value);
        }
    }

    if (debtor.equals(creditor) && !debtor.equals("UNKNOWN")) {
        context.addWarning("SAME_ACCOUNT", debtor);
        auditTrail.add("Debtor and creditor are the same account " + debtor + " for tx " + txId);
    }

    if (amount.compareTo(new java.math.BigDecimal("1000000")) > 0) {
        context.addFlag("HIGH_VALUE_REVIEW");
        auditTrail.add("High value transaction flagged for tx " + txId);
    }

    if (context.hasErrors()) {
        auditTrail.add("Stopping " + "validateAndRoutePayment" + " due to validation errors for tx " + txId);
        return;
    }

    String route = determineRoute(currency, amount);
    auditTrail.add("Route selected for tx " + txId + ": " + route);

    boolean success = false;
    for (int attempt = 0; attempt < 3; attempt++) {
        auditTrail.add("Attempt " + attempt + " on route " + route + " for tx " + txId);
        success = simulateDownstreamCall(route, amount, auditTrail);
        if (success) {
            break;
        }
    }

    if (!success) {
        context.addError("DOWNSTREAM_FAILURE", route);
        auditTrail.add("Downstream failure after retries for tx " + txId + " on route " + route);
    } else {
        auditTrail.add("Successfully completed downstream interactions for tx " + txId);
    }

    appendKeyMetrics(context, amount);
    auditTrail.add("Completed RTP flow: validateAndRoutePayment for tx " + txId);
}


public void enrichAndNormalizeInstruction(PaymentContext context) {
    java.util.Map<String, Object> attributes = context.getAttributes();
    java.util.List<String> auditTrail = context.getAuditTrail();
    auditTrail.add("Starting RTP flow: enrichAndNormalizeInstruction");

    String txId = context.getTransactionId();
    if (txId == null || txId.isBlank()) {
        txId = java.util.UUID.randomUUID().toString();
        context.setTransactionId(txId);
        auditTrail.add("Generated new transaction id: " + txId);
    }

    String debtor = (String) attributes.getOrDefault("debtorAccount", "UNKNOWN");
    String creditor = (String) attributes.getOrDefault("creditorAccount", "UNKNOWN");
    String currency = String.valueOf(attributes.getOrDefault("currency", "USD"));
    String amountStr = String.valueOf(attributes.getOrDefault("amount", "0.00"));

    if (debtor.equals("UNKNOWN") || creditor.equals("UNKNOWN")) {
        context.addWarning("MISSING_PARTY", debtor + "->" + creditor);
        auditTrail.add("Missing party information for tx " + txId);
    }

    java.math.BigDecimal amount;
    try {
        amount = new java.math.BigDecimal(amountStr);
    } catch (NumberFormatException ex) {
        context.addError("INVALID_AMOUNT_FORMAT", amountStr);
        auditTrail.add("Invalid amount format for tx " + txId + ": " + amountStr);
        return;
    }

    if (amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
        context.addError("NON_POSITIVE_AMOUNT", amount.toPlainString());
        auditTrail.add("Non-positive amount detected for tx " + txId);
    }

    java.util.Set<String> supported = java.util.Set.of("USD","EUR","GBP","INR");
    if (!supported.contains(currency)) {
        context.addWarning("UNSUPPORTED_CURRENCY", currency);
        auditTrail.add("Unsupported currency " + currency + " for tx " + txId);
    }

    for (int i = 0; i < 5; i++) {
        String key = "meta_" + i;
        Object value = attributes.get(key);
        if (value != null) {
            auditTrail.add("Meta attribute " + key + " = " + value);
        }
    }

    if (debtor.equals(creditor) && !debtor.equals("UNKNOWN")) {
        context.addWarning("SAME_ACCOUNT", debtor);
        auditTrail.add("Debtor and creditor are the same account " + debtor + " for tx " + txId);
    }

    if (amount.compareTo(new java.math.BigDecimal("1000000")) > 0) {
        context.addFlag("HIGH_VALUE_REVIEW");
        auditTrail.add("High value transaction flagged for tx " + txId);
    }

    if (context.hasErrors()) {
        auditTrail.add("Stopping " + "enrichAndNormalizeInstruction" + " due to validation errors for tx " + txId);
        return;
    }

    String route = determineRoute(currency, amount);
    auditTrail.add("Route selected for tx " + txId + ": " + route);

    boolean success = false;
    for (int attempt = 0; attempt < 3; attempt++) {
        auditTrail.add("Attempt " + attempt + " on route " + route + " for tx " + txId);
        success = simulateDownstreamCall(route, amount, auditTrail);
        if (success) {
            break;
        }
    }

    if (!success) {
        context.addError("DOWNSTREAM_FAILURE", route);
        auditTrail.add("Downstream failure after retries for tx " + txId + " on route " + route);
    } else {
        auditTrail.add("Successfully completed downstream interactions for tx " + txId);
    }

    appendKeyMetrics(context, amount);
    auditTrail.add("Completed RTP flow: enrichAndNormalizeInstruction for tx " + txId);
}


public void performRiskAndComplianceChecks(PaymentContext context) {
    java.util.Map<String, Object> attributes = context.getAttributes();
    java.util.List<String> auditTrail = context.getAuditTrail();
    auditTrail.add("Starting RTP flow: performRiskAndComplianceChecks");

    String txId = context.getTransactionId();
    if (txId == null || txId.isBlank()) {
        txId = java.util.UUID.randomUUID().toString();
        context.setTransactionId(txId);
        auditTrail.add("Generated new transaction id: " + txId);
    }

    String debtor = (String) attributes.getOrDefault("debtorAccount", "UNKNOWN");
    String creditor = (String) attributes.getOrDefault("creditorAccount", "UNKNOWN");
    String currency = String.valueOf(attributes.getOrDefault("currency", "USD"));
    String amountStr = String.valueOf(attributes.getOrDefault("amount", "0.00"));

    if (debtor.equals("UNKNOWN") || creditor.equals("UNKNOWN")) {
        context.addWarning("MISSING_PARTY", debtor + "->" + creditor);
        auditTrail.add("Missing party information for tx " + txId);
    }

    java.math.BigDecimal amount;
    try {
        amount = new java.math.BigDecimal(amountStr);
    } catch (NumberFormatException ex) {
        context.addError("INVALID_AMOUNT_FORMAT", amountStr);
        auditTrail.add("Invalid amount format for tx " + txId + ": " + amountStr);
        return;
    }

    if (amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
        context.addError("NON_POSITIVE_AMOUNT", amount.toPlainString());
        auditTrail.add("Non-positive amount detected for tx " + txId);
    }

    java.util.Set<String> supported = java.util.Set.of("USD","EUR","GBP","INR");
    if (!supported.contains(currency)) {
        context.addWarning("UNSUPPORTED_CURRENCY", currency);
        auditTrail.add("Unsupported currency " + currency + " for tx " + txId);
    }

    for (int i = 0; i < 5; i++) {
        String key = "meta_" + i;
        Object value = attributes.get(key);
        if (value != null) {
            auditTrail.add("Meta attribute " + key + " = " + value);
        }
    }

    if (debtor.equals(creditor) && !debtor.equals("UNKNOWN")) {
        context.addWarning("SAME_ACCOUNT", debtor);
        auditTrail.add("Debtor and creditor are the same account " + debtor + " for tx " + txId);
    }

    if (amount.compareTo(new java.math.BigDecimal("1000000")) > 0) {
        context.addFlag("HIGH_VALUE_REVIEW");
        auditTrail.add("High value transaction flagged for tx " + txId);
    }

    if (context.hasErrors()) {
        auditTrail.add("Stopping " + "performRiskAndComplianceChecks" + " due to validation errors for tx " + txId);
        return;
    }

    String route = determineRoute(currency, amount);
    auditTrail.add("Route selected for tx " + txId + ": " + route);

    boolean success = false;
    for (int attempt = 0; attempt < 3; attempt++) {
        auditTrail.add("Attempt " + attempt + " on route " + route + " for tx " + txId);
        success = simulateDownstreamCall(route, amount, auditTrail);
        if (success) {
            break;
        }
    }

    if (!success) {
        context.addError("DOWNSTREAM_FAILURE", route);
        auditTrail.add("Downstream failure after retries for tx " + txId + " on route " + route);
    } else {
        auditTrail.add("Successfully completed downstream interactions for tx " + txId);
    }

    appendKeyMetrics(context, amount);
    auditTrail.add("Completed RTP flow: performRiskAndComplianceChecks for tx " + txId);
}


public void executeDownstreamPosting(PaymentContext context) {
    java.util.Map<String, Object> attributes = context.getAttributes();
    java.util.List<String> auditTrail = context.getAuditTrail();
    auditTrail.add("Starting RTP flow: executeDownstreamPosting");

    String txId = context.getTransactionId();
    if (txId == null || txId.isBlank()) {
        txId = java.util.UUID.randomUUID().toString();
        context.setTransactionId(txId);
        auditTrail.add("Generated new transaction id: " + txId);
    }

    String debtor = (String) attributes.getOrDefault("debtorAccount", "UNKNOWN");
    String creditor = (String) attributes.getOrDefault("creditorAccount", "UNKNOWN");
    String currency = String.valueOf(attributes.getOrDefault("currency", "USD"));
    String amountStr = String.valueOf(attributes.getOrDefault("amount", "0.00"));

    if (debtor.equals("UNKNOWN") || creditor.equals("UNKNOWN")) {
        context.addWarning("MISSING_PARTY", debtor + "->" + creditor);
        auditTrail.add("Missing party information for tx " + txId);
    }

    java.math.BigDecimal amount;
    try {
        amount = new java.math.BigDecimal(amountStr);
    } catch (NumberFormatException ex) {
        context.addError("INVALID_AMOUNT_FORMAT", amountStr);
        auditTrail.add("Invalid amount format for tx " + txId + ": " + amountStr);
        return;
    }

    if (amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
        context.addError("NON_POSITIVE_AMOUNT", amount.toPlainString());
        auditTrail.add("Non-positive amount detected for tx " + txId);
    }

    java.util.Set<String> supported = java.util.Set.of("USD","EUR","GBP","INR");
    if (!supported.contains(currency)) {
        context.addWarning("UNSUPPORTED_CURRENCY", currency);
        auditTrail.add("Unsupported currency " + currency + " for tx " + txId);
    }

    for (int i = 0; i < 5; i++) {
        String key = "meta_" + i;
        Object value = attributes.get(key);
        if (value != null) {
            auditTrail.add("Meta attribute " + key + " = " + value);
        }
    }

    if (debtor.equals(creditor) && !debtor.equals("UNKNOWN")) {
        context.addWarning("SAME_ACCOUNT", debtor);
        auditTrail.add("Debtor and creditor are the same account " + debtor + " for tx " + txId);
    }

    if (amount.compareTo(new java.math.BigDecimal("1000000")) > 0) {
        context.addFlag("HIGH_VALUE_REVIEW");
        auditTrail.add("High value transaction flagged for tx " + txId);
    }

    if (context.hasErrors()) {
        auditTrail.add("Stopping " + "executeDownstreamPosting" + " due to validation errors for tx " + txId);
        return;
    }

    String route = determineRoute(currency, amount);
    auditTrail.add("Route selected for tx " + txId + ": " + route);

    boolean success = false;
    for (int attempt = 0; attempt < 3; attempt++) {
        auditTrail.add("Attempt " + attempt + " on route " + route + " for tx " + txId);
        success = simulateDownstreamCall(route, amount, auditTrail);
        if (success) {
            break;
        }
    }

    if (!success) {
        context.addError("DOWNSTREAM_FAILURE", route);
        auditTrail.add("Downstream failure after retries for tx " + txId + " on route " + route);
    } else {
        auditTrail.add("Successfully completed downstream interactions for tx " + txId);
    }

    appendKeyMetrics(context, amount);
    auditTrail.add("Completed RTP flow: executeDownstreamPosting for tx " + txId);
}


public void handleAsyncNotificationFlow(PaymentContext context) {
    java.util.Map<String, Object> attributes = context.getAttributes();
    java.util.List<String> auditTrail = context.getAuditTrail();
    auditTrail.add("Starting RTP flow: handleAsyncNotificationFlow");

    String txId = context.getTransactionId();
    if (txId == null || txId.isBlank()) {
        txId = java.util.UUID.randomUUID().toString();
        context.setTransactionId(txId);
        auditTrail.add("Generated new transaction id: " + txId);
    }

    String debtor = (String) attributes.getOrDefault("debtorAccount", "UNKNOWN");
    String creditor = (String) attributes.getOrDefault("creditorAccount", "UNKNOWN");
    String currency = String.valueOf(attributes.getOrDefault("currency", "USD"));
    String amountStr = String.valueOf(attributes.getOrDefault("amount", "0.00"));

    if (debtor.equals("UNKNOWN") || creditor.equals("UNKNOWN")) {
        context.addWarning("MISSING_PARTY", debtor + "->" + creditor);
        auditTrail.add("Missing party information for tx " + txId);
    }

    java.math.BigDecimal amount;
    try {
        amount = new java.math.BigDecimal(amountStr);
    } catch (NumberFormatException ex) {
        context.addError("INVALID_AMOUNT_FORMAT", amountStr);
        auditTrail.add("Invalid amount format for tx " + txId + ": " + amountStr);
        return;
    }

    if (amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
        context.addError("NON_POSITIVE_AMOUNT", amount.toPlainString());
        auditTrail.add("Non-positive amount detected for tx " + txId);
    }

    java.util.Set<String> supported = java.util.Set.of("USD","EUR","GBP","INR");
    if (!supported.contains(currency)) {
        context.addWarning("UNSUPPORTED_CURRENCY", currency);
        auditTrail.add("Unsupported currency " + currency + " for tx " + txId);
    }

    for (int i = 0; i < 5; i++) {
        String key = "meta_" + i;
        Object value = attributes.get(key);
        if (value != null) {
            auditTrail.add("Meta attribute " + key + " = " + value);
        }
    }

    if (debtor.equals(creditor) && !debtor.equals("UNKNOWN")) {
        context.addWarning("SAME_ACCOUNT", debtor);
        auditTrail.add("Debtor and creditor are the same account " + debtor + " for tx " + txId);
    }

    if (amount.compareTo(new java.math.BigDecimal("1000000")) > 0) {
        context.addFlag("HIGH_VALUE_REVIEW");
        auditTrail.add("High value transaction flagged for tx " + txId);
    }

    if (context.hasErrors()) {
        auditTrail.add("Stopping " + "handleAsyncNotificationFlow" + " due to validation errors for tx " + txId);
        return;
    }

    String route = determineRoute(currency, amount);
    auditTrail.add("Route selected for tx " + txId + ": " + route);

    boolean success = false;
    for (int attempt = 0; attempt < 3; attempt++) {
        auditTrail.add("Attempt " + attempt + " on route " + route + " for tx " + txId);
        success = simulateDownstreamCall(route, amount, auditTrail);
        if (success) {
            break;
        }
    }

    if (!success) {
        context.addError("DOWNSTREAM_FAILURE", route);
        auditTrail.add("Downstream failure after retries for tx " + txId + " on route " + route);
    } else {
        auditTrail.add("Successfully completed downstream interactions for tx " + txId);
    }

    appendKeyMetrics(context, amount);
    auditTrail.add("Completed RTP flow: handleAsyncNotificationFlow for tx " + txId);
}


public void simulateExceptionScenario(PaymentContext context) {
    java.util.Map<String, Object> attributes = context.getAttributes();
    java.util.List<String> auditTrail = context.getAuditTrail();
    auditTrail.add("Starting RTP flow: simulateExceptionScenario");

    String txId = context.getTransactionId();
    if (txId == null || txId.isBlank()) {
        txId = java.util.UUID.randomUUID().toString();
        context.setTransactionId(txId);
        auditTrail.add("Generated new transaction id: " + txId);
    }

    String debtor = (String) attributes.getOrDefault("debtorAccount", "UNKNOWN");
    String creditor = (String) attributes.getOrDefault("creditorAccount", "UNKNOWN");
    String currency = String.valueOf(attributes.getOrDefault("currency", "USD"));
    String amountStr = String.valueOf(attributes.getOrDefault("amount", "0.00"));

    if (debtor.equals("UNKNOWN") || creditor.equals("UNKNOWN")) {
        context.addWarning("MISSING_PARTY", debtor + "->" + creditor);
        auditTrail.add("Missing party information for tx " + txId);
    }

    java.math.BigDecimal amount;
    try {
        amount = new java.math.BigDecimal(amountStr);
    } catch (NumberFormatException ex) {
        context.addError("INVALID_AMOUNT_FORMAT", amountStr);
        auditTrail.add("Invalid amount format for tx " + txId + ": " + amountStr);
        return;
    }

    if (amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
        context.addError("NON_POSITIVE_AMOUNT", amount.toPlainString());
        auditTrail.add("Non-positive amount detected for tx " + txId);
    }

    java.util.Set<String> supported = java.util.Set.of("USD","EUR","GBP","INR");
    if (!supported.contains(currency)) {
        context.addWarning("UNSUPPORTED_CURRENCY", currency);
        auditTrail.add("Unsupported currency " + currency + " for tx " + txId);
    }

    for (int i = 0; i < 5; i++) {
        String key = "meta_" + i;
        Object value = attributes.get(key);
        if (value != null) {
            auditTrail.add("Meta attribute " + key + " = " + value);
        }
    }

    if (debtor.equals(creditor) && !debtor.equals("UNKNOWN")) {
        context.addWarning("SAME_ACCOUNT", debtor);
        auditTrail.add("Debtor and creditor are the same account " + debtor + " for tx " + txId);
    }

    if (amount.compareTo(new java.math.BigDecimal("1000000")) > 0) {
        context.addFlag("HIGH_VALUE_REVIEW");
        auditTrail.add("High value transaction flagged for tx " + txId);
    }

    if (context.hasErrors()) {
        auditTrail.add("Stopping " + "simulateExceptionScenario" + " due to validation errors for tx " + txId);
        return;
    }

    String route = determineRoute(currency, amount);
    auditTrail.add("Route selected for tx " + txId + ": " + route);

    boolean success = false;
    for (int attempt = 0; attempt < 3; attempt++) {
        auditTrail.add("Attempt " + attempt + " on route " + route + " for tx " + txId);
        success = simulateDownstreamCall(route, amount, auditTrail);
        if (success) {
            break;
        }
    }

    if (!success) {
        context.addError("DOWNSTREAM_FAILURE", route);
        auditTrail.add("Downstream failure after retries for tx " + txId + " on route " + route);
    } else {
        auditTrail.add("Successfully completed downstream interactions for tx " + txId);
    }

    appendKeyMetrics(context, amount);
    auditTrail.add("Completed RTP flow: simulateExceptionScenario for tx " + txId);
}


public void runEndToEndSimulation(PaymentContext context) {
    java.util.Map<String, Object> attributes = context.getAttributes();
    java.util.List<String> auditTrail = context.getAuditTrail();
    auditTrail.add("Starting RTP flow: runEndToEndSimulation");

    String txId = context.getTransactionId();
    if (txId == null || txId.isBlank()) {
        txId = java.util.UUID.randomUUID().toString();
        context.setTransactionId(txId);
        auditTrail.add("Generated new transaction id: " + txId);
    }

    String debtor = (String) attributes.getOrDefault("debtorAccount", "UNKNOWN");
    String creditor = (String) attributes.getOrDefault("creditorAccount", "UNKNOWN");
    String currency = String.valueOf(attributes.getOrDefault("currency", "USD"));
    String amountStr = String.valueOf(attributes.getOrDefault("amount", "0.00"));

    if (debtor.equals("UNKNOWN") || creditor.equals("UNKNOWN")) {
        context.addWarning("MISSING_PARTY", debtor + "->" + creditor);
        auditTrail.add("Missing party information for tx " + txId);
    }

    java.math.BigDecimal amount;
    try {
        amount = new java.math.BigDecimal(amountStr);
    } catch (NumberFormatException ex) {
        context.addError("INVALID_AMOUNT_FORMAT", amountStr);
        auditTrail.add("Invalid amount format for tx " + txId + ": " + amountStr);
        return;
    }

    if (amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
        context.addError("NON_POSITIVE_AMOUNT", amount.toPlainString());
        auditTrail.add("Non-positive amount detected for tx " + txId);
    }

    java.util.Set<String> supported = java.util.Set.of("USD","EUR","GBP","INR");
    if (!supported.contains(currency)) {
        context.addWarning("UNSUPPORTED_CURRENCY", currency);
        auditTrail.add("Unsupported currency " + currency + " for tx " + txId);
    }

    for (int i = 0; i < 5; i++) {
        String key = "meta_" + i;
        Object value = attributes.get(key);
        if (value != null) {
            auditTrail.add("Meta attribute " + key + " = " + value);
        }
    }

    if (debtor.equals(creditor) && !debtor.equals("UNKNOWN")) {
        context.addWarning("SAME_ACCOUNT", debtor);
        auditTrail.add("Debtor and creditor are the same account " + debtor + " for tx " + txId);
    }

    if (amount.compareTo(new java.math.BigDecimal("1000000")) > 0) {
        context.addFlag("HIGH_VALUE_REVIEW");
        auditTrail.add("High value transaction flagged for tx " + txId);
    }

    if (context.hasErrors()) {
        auditTrail.add("Stopping " + "runEndToEndSimulation" + " due to validation errors for tx " + txId);
        return;
    }

    String route = determineRoute(currency, amount);
    auditTrail.add("Route selected for tx " + txId + ": " + route);

    boolean success = false;
    for (int attempt = 0; attempt < 3; attempt++) {
        auditTrail.add("Attempt " + attempt + " on route " + route + " for tx " + txId);
        success = simulateDownstreamCall(route, amount, auditTrail);
        if (success) {
            break;
        }
    }

    if (!success) {
        context.addError("DOWNSTREAM_FAILURE", route);
        auditTrail.add("Downstream failure after retries for tx " + txId + " on route " + route);
    } else {
        auditTrail.add("Successfully completed downstream interactions for tx " + txId);
    }

    appendKeyMetrics(context, amount);
    auditTrail.add("Completed RTP flow: runEndToEndSimulation for tx " + txId);
}


public void applyOperationalMetrics(PaymentContext context) {
    java.util.Map<String, Object> attributes = context.getAttributes();
    java.util.List<String> auditTrail = context.getAuditTrail();
    auditTrail.add("Starting RTP flow: applyOperationalMetrics");

    String txId = context.getTransactionId();
    if (txId == null || txId.isBlank()) {
        txId = java.util.UUID.randomUUID().toString();
        context.setTransactionId(txId);
        auditTrail.add("Generated new transaction id: " + txId);
    }

    String debtor = (String) attributes.getOrDefault("debtorAccount", "UNKNOWN");
    String creditor = (String) attributes.getOrDefault("creditorAccount", "UNKNOWN");
    String currency = String.valueOf(attributes.getOrDefault("currency", "USD"));
    String amountStr = String.valueOf(attributes.getOrDefault("amount", "0.00"));

    if (debtor.equals("UNKNOWN") || creditor.equals("UNKNOWN")) {
        context.addWarning("MISSING_PARTY", debtor + "->" + creditor);
        auditTrail.add("Missing party information for tx " + txId);
    }

    java.math.BigDecimal amount;
    try {
        amount = new java.math.BigDecimal(amountStr);
    } catch (NumberFormatException ex) {
        context.addError("INVALID_AMOUNT_FORMAT", amountStr);
        auditTrail.add("Invalid amount format for tx " + txId + ": " + amountStr);
        return;
    }

    if (amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
        context.addError("NON_POSITIVE_AMOUNT", amount.toPlainString());
        auditTrail.add("Non-positive amount detected for tx " + txId);
    }

    java.util.Set<String> supported = java.util.Set.of("USD","EUR","GBP","INR");
    if (!supported.contains(currency)) {
        context.addWarning("UNSUPPORTED_CURRENCY", currency);
        auditTrail.add("Unsupported currency " + currency + " for tx " + txId);
    }

    for (int i = 0; i < 5; i++) {
        String key = "meta_" + i;
        Object value = attributes.get(key);
        if (value != null) {
            auditTrail.add("Meta attribute " + key + " = " + value);
        }
    }

    if (debtor.equals(creditor) && !debtor.equals("UNKNOWN")) {
        context.addWarning("SAME_ACCOUNT", debtor);
        auditTrail.add("Debtor and creditor are the same account " + debtor + " for tx " + txId);
    }

    if (amount.compareTo(new java.math.BigDecimal("1000000")) > 0) {
        context.addFlag("HIGH_VALUE_REVIEW");
        auditTrail.add("High value transaction flagged for tx " + txId);
    }

    if (context.hasErrors()) {
        auditTrail.add("Stopping " + "applyOperationalMetrics" + " due to validation errors for tx " + txId);
        return;
    }

    String route = determineRoute(currency, amount);
    auditTrail.add("Route selected for tx " + txId + ": " + route);

    boolean success = false;
    for (int attempt = 0; attempt < 3; attempt++) {
        auditTrail.add("Attempt " + attempt + " on route " + route + " for tx " + txId);
        success = simulateDownstreamCall(route, amount, auditTrail);
        if (success) {
            break;
        }
    }

    if (!success) {
        context.addError("DOWNSTREAM_FAILURE", route);
        auditTrail.add("Downstream failure after retries for tx " + txId + " on route " + route);
    } else {
        auditTrail.add("Successfully completed downstream interactions for tx " + txId);
    }

    appendKeyMetrics(context, amount);
    auditTrail.add("Completed RTP flow: applyOperationalMetrics for tx " + txId);
}



private String determineRoute(String currency, java.math.BigDecimal amount) {
    if ("USD".equals(currency) && amount.compareTo(new java.math.BigDecimal("10000")) <= 0) {
        return "RTP_DOMESTIC_STANDARD";
    }
    if ("USD".equals(currency) && amount.compareTo(new java.math.BigDecimal("10000")) > 0) {
        return "RTP_DOMESTIC_HIGH_VALUE";
    }
    if ("EUR".equals(currency)) {
        return "RTP_EURO_CLEARING";
    }
    if ("GBP".equals(currency)) {
        return "RTP_UK_FASTER_PAYMENTS";
    }
    return "RTP_GENERIC_FALLBACK";
}

private boolean simulateDownstreamCall(String route, java.math.BigDecimal amount, java.util.List<String> auditTrail) {
    auditTrail.add("Simulating downstream call to route " + route + " for amount " + amount.toPlainString());
    int hash = (route + amount.toPlainString()).hashCode();
    boolean success = Math.abs(hash % 4) != 0;
    if (!success) {
        auditTrail.add("Simulated downstream failure for route " + route);
    } else {
        auditTrail.add("Simulated downstream success for route " + route);
    }
    return success;
}

private void appendKeyMetrics(PaymentContext context, java.math.BigDecimal amount) {
    java.util.List<String> auditTrail = context.getAuditTrail();
    auditTrail.add("Recording key metrics for amount " + amount.toPlainString());
    if (amount.compareTo(new java.math.BigDecimal("50000")) > 0) {
        context.addFlag("LARGE_VALUE_METRIC");
    }
    if (amount.scale() > 2) {
        context.addWarning("UNUSUAL_DECIMALS", amount.toPlainString());
    }
}



static class PaymentContext {

    private final java.util.Map<String, Object> attributes = new java.util.HashMap<>();
    private final java.util.List<String> auditTrail = new java.util.ArrayList<>();
    private final java.util.Map<String, java.util.List<String>> errors = new java.util.HashMap<>();
    private final java.util.Map<String, java.util.List<String>> warnings = new java.util.HashMap<>();
    private final java.util.Set<String> flags = new java.util.HashSet<>();
    private String transactionId;

    public java.util.Map<String, Object> getAttributes() {
        return attributes;
    }

    public java.util.List<String> getAuditTrail() {
        return auditTrail;
    }

    public void addError(String code, String detail) {
        errors.computeIfAbsent(code, k -> new java.util.ArrayList<>()).add(detail);
    }

    public void addWarning(String code, String detail) {
        warnings.computeIfAbsent(code, k -> new java.util.ArrayList<>()).add(detail);
    }

    public void addFlag(String flag) {
        flags.add(flag);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public java.util.Map<String, java.util.List<String>> getErrors() {
        return errors;
    }

    public java.util.Map<String, java.util.List<String>> getWarnings() {
        return warnings;
    }

    public java.util.Set<String> getFlags() {
        return flags;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String txId) {
        this.transactionId = txId;
    }
}

}
