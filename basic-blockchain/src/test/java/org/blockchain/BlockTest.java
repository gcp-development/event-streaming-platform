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

    public void testGetHash() throws NoSuchAlgorithmException {
        Block bk;
        int index = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int proof = 100;
        List<Transaction> tx;
        String previousHash = "Genesis Block";
        MessageDigest md;
        String word;
        byte[] wordConvertToBytes;
        StringBuilder hash;

        tx = new ArrayList<>();
        tx.add(new Transaction(10, "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "4A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"));
        tx.add(new Transaction(20, "2A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "5A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"));
        tx.add(new Transaction(30, "3A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "6A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"));
        bk = new Block(index, timestamp, tx, proof, previousHash);

        word = Integer.toString(index) + timestamp.toString() + Integer.toString(60) + Integer.toString(proof) + previousHash;
        md = MessageDigest.getInstance("SHA3-256");
        wordConvertToBytes = md.digest(word.getBytes(StandardCharsets.UTF_8));
        hash = new StringBuilder();
        for (byte c : wordConvertToBytes) {
            hash.append(String.format("%02x", c));
        }
        assertEquals(hash.toString(), bk.getHash());
    }
}
