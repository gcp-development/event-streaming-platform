package org.blockchain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@JsonRootName(value = "ParticipantsPool")
@JsonIgnoreProperties(ignoreUnknown = true)
//pool of network participants that are staking their coins.
public class ParticipantsPool {
    @JsonProperty("Validators")
    private List<Validator> validators;
    @JsonProperty("ValidateTransactionsFees")
    private long validateTransactionsFees;

    public ParticipantsPool() {
        this.validators = new ArrayList<Validator>();
        this.validateTransactionsFees=0;
    }
    public ParticipantsPool(List<Validator> validators,long validateTransactionsFees) {
        this.validators = validators;
        this.validateTransactionsFees=validateTransactionsFees;
    }
    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public long getValidateTransactionsFees() {
        return validateTransactionsFees;
    }

    public void setValidateTransactionsFees(long validateTransactionsFees) {
        this.validateTransactionsFees = validateTransactionsFees;
    }

    private long getSumTokens() {
        long sumTokens = 0;

        for (Iterator<Validator> i = validators.iterator(); i.hasNext(); ) {
            sumTokens = sumTokens + i.next().getTokens();
        }
        return sumTokens;
    }

    public Validator getBockValidator() {
        Validator validator= null, item ;
        long randomNumber;

        randomNumber = ThreadLocalRandom.current().nextLong(0, this.getSumTokens() + 1);
        for (Iterator<Validator> i = validators.iterator(); i.hasNext(); ) {
            item = i.next();
            if (randomNumber < item.getTokens()) {
                validator = item;
                break;
            }
            randomNumber -= item.getTokens();
        }
        return validator;
    }
}