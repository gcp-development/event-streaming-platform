package org.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonRootName(value = "Block")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Block {
    @JsonProperty("Index")
    private int nonce;
    @JsonProperty("Timestamp")
    private Timestamp timestamp;
    @JsonProperty("Transactions")
    private List<Transaction> transactions;
    @JsonProperty("PreviousHash")
    private String previousHash;

    public Block() {
        this.nonce = -1;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.transactions = new ArrayList<Transaction>();
        this.previousHash = new String();
    }

    public Block(int nonce, Timestamp timestamp, List<Transaction> transactions, String previousHash) {
        this.nonce = nonce;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.previousHash = previousHash;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
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

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    private long getSumAmount() {
        long sumAmount = 0;

        for (Iterator<Transaction> i = transactions.iterator(); i.hasNext(); ) {
            sumAmount = sumAmount + i.next().getAmount();
        }
        return sumAmount;
    }

    public String getHash() throws NoSuchAlgorithmException {
        String word;
        MessageDigest md;
        byte[] wordConvertToBytes;
        StringBuilder hash;

        word = Integer.toString(nonce) + timestamp.toString() + Long.toString(getSumAmount()) + previousHash;
        md = MessageDigest.getInstance("SHA3-256");
        wordConvertToBytes = md.digest(word.getBytes(StandardCharsets.UTF_8));
        hash = new StringBuilder();
        for (byte c : wordConvertToBytes) {
            hash.append(String.format("%02x", c));
        }
        return hash.toString();
    }
}