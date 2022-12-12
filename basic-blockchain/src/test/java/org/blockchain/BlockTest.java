package org.blockchain;

import junit.framework.TestCase;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BlockTest extends TestCase {
    public BlockTest(String testName) {
        super(testName);
    }

    public void testBlock() {
        final int NONCE = 0;
        final Timestamp TIMESTAMP = new Timestamp(System.currentTimeMillis());
        final Transaction TX = new Transaction(10, "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "4A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
        final String PREVIOUS_HASH = "Genesis Block";
        Block bk;
        List<Transaction> ls;

        ls = new ArrayList<>();
        ls.add(TX);
        bk = new Block();
        bk.setNonce(NONCE);
        bk.setTimestamp(TIMESTAMP);
        bk.setTransactions(ls);
        bk.setPreviousHash(PREVIOUS_HASH);

        assertEquals(NONCE, bk.getNonce());
        assertEquals(TIMESTAMP, bk.getTimestamp());
        assertEquals(ls, bk.getTransactions());
        assertEquals(PREVIOUS_HASH, bk.getPreviousHash());
    }

    public void testGetHash() throws NoSuchAlgorithmException {
        Block bk;
        final int NONCE = 0;
        final Timestamp TIMESTAMP = new Timestamp(System.currentTimeMillis());
        final String PREVIOUS_HASH = "Genesis Block";
        final long AMOUNT_TX1 = 10;
        final long AMOUNT_TX2 = 20;
        final long AMOUNT_TX3 = 30;
        List<Transaction> tx;
        MessageDigest md;
        String word;
        byte[] wordConvertToBytes;
        StringBuilder hash;

        tx = new ArrayList<>();
        tx.add(new Transaction(AMOUNT_TX1, "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "4A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"));
        tx.add(new Transaction(AMOUNT_TX2, "2A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "5A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"));
        tx.add(new Transaction(AMOUNT_TX3, "3A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "6A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"));
        bk = new Block(NONCE, TIMESTAMP, tx, PREVIOUS_HASH);

        word = Integer.toString(NONCE) + TIMESTAMP.toString() + Long.toString(AMOUNT_TX1 + AMOUNT_TX2 + AMOUNT_TX3) + PREVIOUS_HASH;
        md = MessageDigest.getInstance("SHA3-256");
        wordConvertToBytes = md.digest(word.getBytes(StandardCharsets.UTF_8));
        hash = new StringBuilder();
        for (byte c : wordConvertToBytes) {
            hash.append(String.format("%02x", c));
        }
        assertEquals(hash.toString(), bk.getHash());
    }
}