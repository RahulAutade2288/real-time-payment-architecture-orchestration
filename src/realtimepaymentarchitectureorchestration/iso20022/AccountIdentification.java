package realtimepaymentarchitectureorchestration.iso20022;

import java.math.*;
import java.time.*;
import java.util.*;


/**
 * Simplified account identification information.
 */
public class AccountIdentification {

    private String iban;
    private String accountNumber;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("iban", iban);
        map.put("accountNumber", accountNumber);
        return map;
    }

    @Override
    public String toString() {
        return "AccountIdentification{" +
                "iban='" + iban + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
