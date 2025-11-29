package realtimepaymentarchitectureorchestration.validation;

import java.math.*;
import java.time.*;
import java.util.*;


/**
 * CutoffTimeValidator performs one aspect of payment validation.
 */
public class CutoffTimeValidator {

    public void validateRequired(String fieldName, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }

    public void validatePositiveAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("amount is required");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    public void validateBusinessHours(Instant timestamp, ZoneId zone) {
        if (timestamp == null) {
            return;
        }
        ZonedDateTime zdt = timestamp.atZone(zone);
        int hour = zdt.getHour();
        if (hour < 6 || hour > 22) {
            throw new IllegalArgumentException("outside of configured business hours: " + hour);
        }
    }

    public void validateCurrency(String currency, Set<String> allowed) {
        if (!allowed.contains(currency)) {
            throw new IllegalArgumentException("unsupported currency: " + currency);
        }
    }

    public List<String> validateAll(Map<String, Object> paymentMap) {
        List<String> result = new ArrayList<>();
        Object amountObj = paymentMap.get("amount");
        if (amountObj instanceof String) {
            try {
                BigDecimal value = new BigDecimal((String) amountObj);
                validatePositiveAmount(value);
                result.add("amount: OK");
            } catch (Exception ex) {
                result.add("amount: " + ex.getMessage());
            }
        } else {
            result.add("amount: missing or not a String");
        }

        Object currency = paymentMap.get("currency");
        if (currency instanceof String) {
            validateRequired("currency", (String) currency);
            result.add("currency: OK");
        } else {
            result.add("currency: missing or not a String");
        }
        return result;
    }
}
