package org.blockchain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public class ValidatorTest extends TestCase {

    public ValidatorTest(String testName) {
        super(testName);
    }

    public void testValidateBlock() throws NoSuchAlgorithmException {
        Validator vl;
        Block bk1, bk2;

        vl = new Validator("Validator1", "", 100);
        bk1 = new Block();
        bk1.setIndex(0);
        bk1.setTimestamp(new Timestamp(System.currentTimeMillis()));
        bk1.setProof(100);
        bk1.setPreviousHash("Genesis Block");

        bk2 = new Block();
        bk2.setIndex(1);
        bk2.setTimestamp(new Timestamp(System.currentTimeMillis()));
        bk2.setProof(100);
        bk2.setPreviousHash(bk1.getHash());

        assertEquals(true, vl.validateBlock(bk1.getHash(), bk2));
    }
}
