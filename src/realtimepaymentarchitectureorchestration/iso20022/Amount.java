package realtimepaymentarchitectureorchestration.iso20022;

import java.math.*;
import java.time.*;
import java.util.*;


/**
 * Simple representation of an amount and its currency.
 */
public class Amount {

    private BigDecimal amount;
    private String currency;

    public Amount() {
    }

    public Amount(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("amount", amount != null ? amount.toPlainString() : null);
        map.put("currency", currency);
        return map;
    }

    public boolean isPositive() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
