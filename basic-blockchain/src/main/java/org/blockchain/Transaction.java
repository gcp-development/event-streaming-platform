package org.blockchain;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;

@JsonRootName(value = "Transaction")
public class Transaction {
    @JsonProperty("Amount")
    private int amount;
    @JsonProperty("Recipient")
    private String recipient;
    @JsonProperty("Sender")
    private String sender;

    public Transaction(int amount, String recipient, String sender) {
        this.amount = amount;
        this.recipient = recipient;
        this.sender = sender;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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