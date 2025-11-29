package realtimepaymentarchitectureorchestration.iso20022;

import java.math.*;
import java.time.*;
import java.util.*;


/**
 * FinancialInstitution is a utility or helper class for ISO 20022-related processing.
 */
public class FinancialInstitution {

    private final Map<String, String> properties = new LinkedHashMap<>();

    public FinancialInstitution() {
        properties.put("createdAt", OffsetDateTime.now().toString());
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public Map<String, String> getAllProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public String dumpAsText() {
        StringBuilder sb = new StringBuilder();
        sb.append("FinancialInstitution properties:\n");
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(" = ").append(entry.getValue()).append('\n');
        }
        return sb.toString();
    }
}
