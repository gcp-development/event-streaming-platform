package org.storage.processor;

import org.blockchain.Block;
import org.blockchain.Transaction;
import java.sql.Timestamp;

public class App {
    public static void main(String[] args) {
        final String BOOTSTRAP_SERVERS = "10.104.90.93:9092";
        final String TRANSACTIONS_TOPIC = "transactions";
        final String BLOCKCHAIN_TOPIC = "blockchain";

        Block bk;
        Transaction tx;

        /*
         def genesis_block(self):
        return {
            'index': 1,
            'timestamp': time(),
            'transactions': [],
            'proof': 100,
            'previous_hash': 1,
        }
         */
        try {
            bk = new Block();
            bk.setIndex(1);
            bk.setTimestamp(new Timestamp(System.currentTimeMillis()));
            bk.setProof(100);
            tx = new Transaction(10, "1111P1eP5QGefi2DMPTfTL5SLmv7DivfNa", "4444P1eP5QGefi2DMPTfTL5SLmv7DivfNa");
            bk.getTransactions().add(tx);
            tx = new Transaction(20, "2222P1eP5QGefi2DMPTfTL5SLmv7DivfNa", "5555P1eP5QGefi2DMPTfTL5SLmv7DivfNa");
            bk.getTransactions().add(tx);
            tx = new Transaction(30, "3333P1eP5QGefi2DMPTfTL5SLmv7DivfNa", "6666P1eP5QGefi2DMPTfTL5SLmv7DivfNa");
            bk.getTransactions().add(tx);
        } catch (Exception e) {
            System.out.println("-------------------------------------------------------------------------------------------------------");
            e.printStackTrace();
            System.out.println("-------------------------------------------------------------------------------------------------------");
        } finally {

        }
    }
}