package realtimepaymentarchitectureorchestration.app;

import java.time.*;
import java.util.*;


/**
 * Bootstrapper wires together the core objects of the demo system.
 * It keeps everything in plain Java so that the architecture can be
 * understood without framework-specific annotations.
 */
public class Bootstrapper {

    private final ApplicationConfig config;

    public Bootstrapper(ApplicationConfig config) {
        this.config = Objects.requireNonNull(config, "config");
    }

    public void start() {
        System.out.println("Bootstrapper starting with config: " + config);
    }

    /**
     * Runs a very small, in-memory sample payment flow.
     */
    public void runDemoPayment() {
        System.out.println("Running demo payment...");

        Map<String, Object> paymentData = new LinkedHashMap<>();
        paymentData.put("debtorAccount", "123456789");
        paymentData.put("creditorAccount", "987654321");
        paymentData.put("amount", "42.50");
        paymentData.put("currency", config.getProperty("payments.currency.default", "USD"));

        realtimepaymentarchitectureorchestration.orchestration.PaymentOrchestrator orchestrator =
                new realtimepaymentarchitectureorchestration.orchestration.PaymentOrchestrator();

        realtimepaymentarchitectureorchestration.orchestration.OrchestrationResult result =
                orchestrator.orchestrateSimple(paymentData);

        System.out.println("Demo payment result: " + result.getStatus());
        System.out.println("Messages:");
        for (String msg : result.getMessages()) {
            System.out.println("  - " + msg);
        }
    }
}
