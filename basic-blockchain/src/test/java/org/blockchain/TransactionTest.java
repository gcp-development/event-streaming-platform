package org.blockchain;

import junit.framework.TestCase;

public class TransactionTest extends TestCase {

    public TransactionTest(String testName) {
        super(testName);
    }

    public void testTransaction() {
        final long AMOUNT = 100;
        final String RECIPIENT = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
        final String SENDER = "4A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
        Transaction tx;

        tx = new Transaction();
        tx.setAmount(AMOUNT);
        tx.setRecipient(RECIPIENT);
        tx.setSender(SENDER);

        assertEquals(AMOUNT, tx.getAmount());
        assertEquals(RECIPIENT, tx.getRecipient());
        assertEquals(SENDER, tx.getSender());
    }
}
