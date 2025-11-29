package realtimepaymentarchitectureorchestration.iso20022;

import java.math.*;
import java.time.*;
import java.util.*;


/**
 * Simplified address used by PartyIdentification.
 */
public class PostalAddress {

    private String country;
    private String townName;
    private String streetName;
    private String buildingNumber;
    private String postCode;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("country", country);
        map.put("townName", townName);
        map.put("streetName", streetName);
        map.put("buildingNumber", buildingNumber);
        map.put("postCode", postCode);
        return map;
    }

    @Override
    public String toString() {
        return "PostalAddress{" +
                "country='" + country + '\'' +
                ", townName='" + townName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
