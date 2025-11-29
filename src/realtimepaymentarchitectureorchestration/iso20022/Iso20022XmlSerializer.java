package realtimepaymentarchitectureorchestration.iso20022;

import java.math.*;
import java.time.*;
import java.util.*;


/**
 * Iso20022XmlSerializer is a utility or helper class for ISO 20022-related processing.
 */
public class Iso20022XmlSerializer {

    private final Map<String, String> properties = new LinkedHashMap<>();

    public Iso20022XmlSerializer() {
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
        sb.append("Iso20022XmlSerializer properties:\n");
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(" = ").append(entry.getValue()).append('\n');
        }
        return sb.toString();
    }
}
