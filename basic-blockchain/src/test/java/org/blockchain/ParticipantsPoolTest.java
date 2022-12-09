package org.blockchain;

import junit.framework.TestCase;

public class ParticipantsPoolTest extends TestCase {
    public ParticipantsPoolTest(String testName) {
        super(testName);
    }

    public void testGetBockValidator() {
        ParticipantsPool pool;
        Validator vl1, vl2, vl3, blockValidator;

        pool = new ParticipantsPool();
        vl1 = new Validator("Validator1", "", 100);
        pool.getValidators().add(vl1);
        vl2 = new Validator("Validator2", "", 700);
        pool.getValidators().add(vl2);
        vl3 = new Validator("Validator3", "", 200);
        pool.getValidators().add(vl3);

        blockValidator = pool.getBockValidator();
        //The probability in getting vl2 is the biggest because the validator2 has the most amount of tokens.
        //Meaning we will get this unit testing failing in some occasions.
        assertEquals(vl2.getName(), blockValidator.getName());
    }
}