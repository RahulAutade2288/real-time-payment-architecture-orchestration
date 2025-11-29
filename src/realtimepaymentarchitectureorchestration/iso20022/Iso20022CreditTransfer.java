package realtimepaymentarchitectureorchestration.iso20022;

import java.math.*;
import java.time.*;
import java.util.*;


/**
 * Minimal ISO 20022-inspired credit transfer object.
 */
public class Iso20022CreditTransfer {

    private String messageId;
    private String instructionId;
    private String endToEndId;
    private PartyIdentification debtor;
    private PartyIdentification creditor;
    private AccountIdentification debtorAccount;
    private AccountIdentification creditorAccount;
    private Amount instructedAmount;
    private String remittanceInformation;
    private OffsetDateTime creationDateTime;

    public Iso20022CreditTransfer() {
        this.creationDateTime = OffsetDateTime.now();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(String instructionId) {
        this.instructionId = instructionId;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    public PartyIdentification getDebtor() {
        return debtor;
    }

    public void setDebtor(PartyIdentification debtor) {
        this.debtor = debtor;
    }

    public PartyIdentification getCreditor() {
        return creditor;
    }

    public void setCreditor(PartyIdentification creditor) {
        this.creditor = creditor;
    }

    public AccountIdentification getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountIdentification debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public AccountIdentification getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(AccountIdentification creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public Amount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(Amount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getRemittanceInformation() {
        return remittanceInformation;
    }

    public void setRemittanceInformation(String remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }

    public OffsetDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(OffsetDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public void validate() {
        if (messageId == null || messageId.isBlank()) {
            throw new IllegalStateException("messageId is required");
        }
        if (endToEndId == null || endToEndId.isBlank()) {
            throw new IllegalStateException("endToEndId is required");
        }
        if (debtor == null || creditor == null) {
            throw new IllegalStateException("debtor and creditor are required");
        }
        if (debtorAccount == null || creditorAccount == null) {
            throw new IllegalStateException("debtorAccount and creditorAccount are required");
        }
        if (instructedAmount == null || instructedAmount.getAmount() == null) {
            throw new IllegalStateException("instructedAmount is required");
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("messageId", messageId);
        map.put("instructionId", instructionId);
        map.put("endToEndId", endToEndId);
        map.put("debtor", debtor != null ? debtor.toMap() : null);
        map.put("creditor", creditor != null ? creditor.toMap() : null);
        map.put("debtorAccount", debtorAccount != null ? debtorAccount.toMap() : null);
        map.put("creditorAccount", creditorAccount != null ? creditorAccount.toMap() : null);
        map.put("instructedAmount", instructedAmount != null ? instructedAmount.toMap() : null);
        map.put("remittanceInformation", remittanceInformation);
        map.put("creationDateTime", creationDateTime != null ? creationDateTime.toString() : null);
        return map;
    }

    @Override
    public String toString() {
        return "Iso20022CreditTransfer{" +
                "messageId='" + messageId + '\'' +
                ", instructionId='" + instructionId + '\'' +
                ", endToEndId='" + endToEndId + '\'' +
                ", debtor=" + debtor +
                ", creditor=" + creditor +
                ", debtorAccount=" + debtorAccount +
                ", creditorAccount=" + creditorAccount +
                ", instructedAmount=" + instructedAmount +
                ", remittanceInformation='" + remittanceInformation + '\'' +
                ", creationDateTime=" + creationDateTime +
                '}';
    }
}
