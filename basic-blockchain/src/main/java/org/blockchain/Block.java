package org.blockchain;

import java.util.List;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonRootName(value = "Block")
public class Block {
    @JsonProperty("Index")
    private int index;
    @JsonProperty("Timestamp")
    private Timestamp timestamp;
    @JsonProperty("Transactions")
    private List<Transaction> transactions;
    @JsonProperty("Proof")
    private int proof;
    @JsonProperty("PreviousHash")
    private String previousHash;

    public Block(int index, Timestamp timestamp, List<Transaction> transactions, int proof, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.proof = proof;
        this.previousHash = previousHash;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getProof() {
        return proof;
    }

    public void setProof(int proof) {
        this.proof = proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
}
