package org.blockchain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonRootName(value = "Transaction")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    @JsonProperty("Amount")
    private long amount;
    @JsonProperty("Recipient")
    private String recipient;
    @JsonProperty("Sender")
    private String sender;

    public Transaction() {
        this.amount = -1;
        this.recipient = new String();
        this.sender = new String();
    }

    public Transaction(long amount, String recipient, String sender) {
        this.amount = amount;
        this.recipient = recipient;
        this.sender = sender;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}