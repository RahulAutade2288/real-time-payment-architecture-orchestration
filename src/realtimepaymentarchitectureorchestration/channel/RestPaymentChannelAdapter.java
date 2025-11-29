package realtimepaymentarchitectureorchestration.channel;

import java.util.LinkedHashMap;
import java.util.Map;

import realtimepaymentarchitectureorchestration.orchestration.PaymentOrchestrator;
import realtimepaymentarchitectureorchestration.orchestration.OrchestrationResult;

/**
 * Simple in-process implementation of PaymentChannelAdapter that behaves like a REST endpoint.
 * It receives a request map, delegates to PaymentOrchestrator, and prints the result.
 */
public class RestPaymentChannelAdapter implements PaymentChannelAdapter {

    private final PaymentOrchestrator orchestrator;

    public RestPaymentChannelAdapter(PaymentOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @Override
    public void submitPayment(Map<String, Object> paymentRequest) {
        Map<String, Object> safeCopy = new LinkedHashMap<>(paymentRequest);
        OrchestrationResult result = orchestrator.orchestrateSimple(safeCopy);
        System.out.println("REST-like channel received result: " + result.getStatus());
        for (String msg : result.getMessages()) {
            System.out.println("  " + msg);
        }
    }
}
