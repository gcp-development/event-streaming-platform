package org.blockchain;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;

public class ParticipantsPoolTest extends TestCase {
    public ParticipantsPoolTest(String testName) {
        super(testName);
    }

    public void testParticipantsPool() {
        final long VALIDATE_TRANSACTIONS_FEES = 7;
        final String NAME = "Validator1";
        final String ADDRESS = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
        final long TOKENS = 100;
        List<Validator> validators;
        Validator validator;
        ParticipantsPool pp;

        validator = new Validator();
        pp = new ParticipantsPool();
        validators = new ArrayList<>();
        validator.setName(NAME);
        validator.setAddress(ADDRESS);
        validator.setTokens(TOKENS);
        validators.add(validator);
        pp.setValidators(validators);
        pp.setValidateTransactionsFees(VALIDATE_TRANSACTIONS_FEES);

        assertEquals(validators, pp.getValidators());
        assertEquals(VALIDATE_TRANSACTIONS_FEES, pp.getValidateTransactionsFees());
    }

    public void testGetBlockValidator() {
        ParticipantsPool pool;
        Validator vl1, vl2, vl3, blockValidator;

        pool = new ParticipantsPool();
        vl1 = new Validator("Validator1", "", 100);
        pool.getValidators().add(vl1);
        vl2 = new Validator("Validator2", "", 700);
        pool.getValidators().add(vl2);
        vl3 = new Validator("Validator3", "", 200);
        pool.getValidators().add(vl3);

        blockValidator = pool.getBlockValidator();
        //The probability in getting vl2 is the biggest because the validator2 has the most amount of tokens.
        //Meaning we will get this unit testing failing in some occasions.
        assertEquals(vl2.getName(), blockValidator.getName());
    }
}