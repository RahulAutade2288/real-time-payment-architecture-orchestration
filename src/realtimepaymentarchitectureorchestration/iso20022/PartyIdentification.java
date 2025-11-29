package realtimepaymentarchitectureorchestration.iso20022;

import java.math.*;
import java.time.*;
import java.util.*;


/**
 * Simplified representation of a party in an ISO 20022 message.
 */
public class PartyIdentification {

    private String name;
    private PostalAddress address;

    public PartyIdentification() {
    }

    public PartyIdentification(String name, PostalAddress address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PostalAddress getAddress() {
        return address;
    }

    public void setAddress(PostalAddress address) {
        this.address = address;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", name);
        map.put("address", address != null ? address.toMap() : null);
        return map;
    }

    @Override
    public String toString() {
        return "PartyIdentification{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
