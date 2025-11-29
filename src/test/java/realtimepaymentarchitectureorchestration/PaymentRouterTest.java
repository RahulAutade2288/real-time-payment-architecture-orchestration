
package realtimepaymentarchitectureorchestration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import realtimepaymentarchitectureorchestration.routing.PaymentRouter;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRouterTest {

    @Test
    @DisplayName("Default rules should route USD to INTERNAL_RT")
    void routeByCurrency_defaultRules() {
        PaymentRouter router = new PaymentRouter();
        assertEquals("INTERNAL_RT", router.routeByCurrency("USD"));
        assertEquals("SEPA_INSTANT", router.routeByCurrency("EUR"));
    }

    @Test
    @DisplayName("Unknown currencies should use DEFAULT_ROUTE")
    void routeByCurrency_default() {
        PaymentRouter router = new PaymentRouter();
        assertEquals("DEFAULT_ROUTE", router.routeByCurrency("JPY"));
    }

    @Test
    @DisplayName("Custom rule should override default")
    void addRule_override() {
        PaymentRouter router = new PaymentRouter();
        router.addRule("JPY", "JAPAN_RT");
        assertEquals("JAPAN_RT", router.routeByCurrency("JPY"));
    }
}
