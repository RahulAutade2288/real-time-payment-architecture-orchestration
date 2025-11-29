package realtimepaymentarchitectureorchestration.app;

import java.time.*;
import java.util.*;


/**
 * Simple in-memory configuration holder.
 * In a real system this would likely be backed by environment variables,
 * configuration servers, or secure vaults.
 */
public class ApplicationConfig {

    private String environmentName;
    private String profileName;
    private Instant buildTimestamp;
    private final Map<String, String> properties = new LinkedHashMap<>();

    public void loadDefaults() {
        this.environmentName = "local";
        this.profileName = "demo";
        this.buildTimestamp = Instant.now();

        properties.put("payments.maxAmount", "100000.00");
        properties.put("payments.currency.default", "USD");
        properties.put("fraud.engine.enabled", "true");
        properties.put("routing.defaultScheme", "INTERNAL_RT");
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Instant getBuildTimestamp() {
        return buildTimestamp;
    }

    public void setBuildTimestamp(Instant buildTimestamp) {
        this.buildTimestamp = buildTimestamp;
    }

    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String toString() {
        return "ApplicationConfig{" +
                "environmentName='" + environmentName + '\'' +
                ", profileName='" + profileName + '\'' +
                ", buildTimestamp=" + buildTimestamp +
                ", properties=" + properties +
                '}';
    }
}
