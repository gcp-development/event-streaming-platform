package org.blockchain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "Validator")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Validator {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("Tokens")
    private long tokens;

    public Validator() {
        this.name = new String();
        this.address = new String();
        this.tokens = 0;
    }

    public Validator(String name, String address, long tokens) {
        this.name = name;
        this.address = address;
        this.tokens = tokens;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setTokens(long tokens) {
        this.tokens = tokens;
    }

    public long getTokens() {
        return tokens;
    }

    public boolean validateBlock(String previousBlockHash, Block newblock) {
        return previousBlockHash.equals(newblock.getPreviousHash());
    }
}