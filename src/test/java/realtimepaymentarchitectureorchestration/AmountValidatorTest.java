
package realtimepaymentarchitectureorchestration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import realtimepaymentarchitectureorchestration.validation.AmountValidator;

import static org.junit.jupiter.api.Assertions.*;

class AmountValidatorTest {

    @Test
    @DisplayName("Positive amount should pass validation")
    void validatePositiveAmount_ok() {
        AmountValidator validator = new AmountValidator();
        assertDoesNotThrow(() -> validator.validatePositiveAmount(new BigDecimal("1.00")));
    }

    @Test
    @DisplayName("Zero or negative amount should fail validation")
    void validatePositiveAmount_fail() {
        AmountValidator validator = new AmountValidator();
        assertThrows(IllegalArgumentException.class, () -> validator.validatePositiveAmount(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> validator.validatePositiveAmount(new BigDecimal("-1.00")));
    }

    @Test
    @DisplayName("validateAll should return messages for invalid input")
    void validateAll_invalid() {
        AmountValidator validator = new AmountValidator();
        Map<String, Object> payment = new HashMap<>();
        payment.put("amount", "not-a-number");
        payment.put("currency", "");

        List<String> messages = validator.validateAll(payment);
        assertEquals(2, messages.size());
        assertTrue(messages.get(0).startsWith("amount:"));
        assertTrue(messages.get(1).startsWith("currency:"));
    }

    @Test
    @DisplayName("validateBusinessHours should fail outside configured hours")
    void validateBusinessHours_outOfHours() {
        AmountValidator validator = new AmountValidator();
        Instant earlyMorning = Instant.parse("2024-01-01T02:00:00Z");
        assertThrows(IllegalArgumentException.class,
                () -> validator.validateBusinessHours(earlyMorning, ZoneId.of("UTC")));
    }
}
