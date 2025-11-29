package realtimepaymentarchitectureorchestration.routing;

import java.util.*;


/**
 * BankRouter encapsulates a small routing strategy.
 */
public class BankRouter {

    private final Map<String, String> rules = new LinkedHashMap<>();

    public BankRouter() {
        rules.put("USD", "INTERNAL_RT");
        rules.put("EUR", "SEPA_INSTANT");
    }

    public String routeByCurrency(String currencyCode) {
        return rules.getOrDefault(currencyCode, "DEFAULT_ROUTE");
    }

    public void addRule(String key, String route) {
        rules.put(key, route);
    }

    public Map<String, String> getRules() {
        return Collections.unmodifiableMap(rules);
    }
}
