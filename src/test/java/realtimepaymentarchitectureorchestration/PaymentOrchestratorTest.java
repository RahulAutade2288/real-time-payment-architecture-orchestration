
package realtimepaymentarchitectureorchestration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import realtimepaymentarchitectureorchestration.orchestration.PaymentOrchestrator;
import realtimepaymentarchitectureorchestration.orchestration.OrchestrationResult;

import static org.junit.jupiter.api.Assertions.*;

class PaymentOrchestratorTest {

    @Test
    @DisplayName("Happy-path orchestration should complete successfully")
    void orchestrateSimple_success() {
        PaymentOrchestrator orchestrator = new PaymentOrchestrator();
        Map<String, Object> payment = new HashMap<>();
        payment.put("debtorAccount", "11111111");
        payment.put("creditorAccount", "22222222");
        payment.put("amount", "10.00");
        payment.put("currency", "USD");

        OrchestrationResult result = orchestrator.orchestrateSimple(payment);

        assertEquals(OrchestrationResult.Status.SUCCESS, result.getStatus());
        assertTrue(result.getMessages().stream().anyMatch(m -> m.contains("Payment completed successfully")));
    }

    @Test
    @DisplayName("Missing debtor account should fail validation")
    void orchestrateSimple_missingDebtor() {
        PaymentOrchestrator orchestrator = new PaymentOrchestrator();
        Map<String, Object> payment = new HashMap<>();
        payment.put("creditorAccount", "22222222");
        payment.put("amount", "10.00");
        payment.put("currency", "USD");

        OrchestrationResult result = orchestrator.orchestrateSimple(payment);

        assertEquals(OrchestrationResult.Status.FAILURE, result.getStatus());
        assertTrue(result.getMessages().stream().anyMatch(m -> m.contains("Debtor account is required")));
    }
}
