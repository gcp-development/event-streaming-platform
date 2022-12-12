package org.storage.processor;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.blockchain.Block;
import org.blockchain.ParticipantsPool;
import org.blockchain.Transaction;
import org.blockchain.Validator;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class App {
    public static void main(String[] args) {
        final String BOOTSTRAP_SERVERS = "10.104.90.93:9092";
        final String TRANSACTIONS_TOPIC = "transactions";
        final String GROUP_ID = "block-processor";
        final String BLOCKCHAIN_TOPIC = "blockchain";
        final String SCHEMA_REGISTRY = "http://schema-registry:8081";
        final int BLOCK_TRANSACTIONS = 3;
        //Network Validators
        final String VALIDATOR1_NAME = "Validator1";
        final String VALIDATOR1_ADDRESS = "v11zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
        final String VALIDATOR2_NAME = "Validator2";
        final String VALIDATOR2_ADDRESS = "v21zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
        final String VALIDATOR3_NAME = "Validator3";
        final String VALIDATOR3_ADDRESS = "v31zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
        final String VALIDATOR4_NAME = "Validator4";
        final String VALIDATOR4_ADDRESS = "v41zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
        final String VALIDATOR5_NAME = "Validator5";
        final String VALIDATOR5_ADDRESS = "v51zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";

        //transactions
        Properties pro;
        KafkaConsumer<String, Transaction> consumer = null;
        ConsumerRecords<String, Transaction> records;
        TopicPartition tp;
        Block bk;
        List<Transaction> txs;
        int index;
        //Block
        Properties proBlock;
        KafkaProducer<String, Block> producer = null;
        ProducerRecord<String, Block> recordBlock;
        String blockhash;

        //Proof of Stake Validators pool
        final long MAX_RANGE = 2000;
        final long MIN_RANGE = 1;
        final long VALIDATION_FEES = 7;
        Validator vl1, vl2, vl3, vl4, vl5, blockValidator;
        ParticipantsPool pp;


        pp = new ParticipantsPool();
        vl1 = new Validator(VALIDATOR1_NAME, VALIDATOR1_ADDRESS, ThreadLocalRandom.current().nextLong(MIN_RANGE, MAX_RANGE + 1));
        pp.getValidators().add(vl1);
        vl2 = new Validator(VALIDATOR2_NAME, VALIDATOR2_ADDRESS, ThreadLocalRandom.current().nextLong(MIN_RANGE, MAX_RANGE + 1));
        pp.getValidators().add(vl2);
        vl3 = new Validator(VALIDATOR3_NAME, VALIDATOR3_ADDRESS, ThreadLocalRandom.current().nextLong(MIN_RANGE, MAX_RANGE + 1));
        pp.getValidators().add(vl3);
        vl4 = new Validator(VALIDATOR4_NAME, VALIDATOR4_ADDRESS, ThreadLocalRandom.current().nextLong(MIN_RANGE, MAX_RANGE + 1));
        pp.getValidators().add(vl4);
        vl5 = new Validator(VALIDATOR5_NAME, VALIDATOR5_ADDRESS, ThreadLocalRandom.current().nextLong(MIN_RANGE, MAX_RANGE + 1));
        pp.getValidators().add(vl5);
        pp.setValidateTransactionsFees(VALIDATION_FEES);


        try {
            //transactions
            pro = new Properties();
            pro.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            pro.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
            pro.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            pro.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonSchemaDeserializer.class.getName());
            pro.setProperty(KafkaJsonSchemaDeserializerConfig.JSON_VALUE_TYPE, Transaction.class.getName());
            pro.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            pro.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            pro.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
            pro.setProperty(KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY);

            //blocks
            proBlock = new Properties();
            proBlock.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            proBlock.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            proBlock.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class.getName());
            proBlock.setProperty(KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY);

            tp = new TopicPartition(TRANSACTIONS_TOPIC, 0);
            consumer = new KafkaConsumer<String, Transaction>(pro);
            consumer.subscribe(Collections.singletonList(TRANSACTIONS_TOPIC));
            System.out.println("Listening for transactions...");
            consumer.poll(0);
            consumer.seekToBeginning(consumer.assignment());
            //consumer.seekToEnd(Collections.singletonList(tp));
            //consumer.commitSync() ;
            //consumer.seek(tp,11);

            index = 0;
            blockhash = "Genesis Block";
            txs = new ArrayList<>();
            while (true) {
                records = consumer.poll(Duration.ofMillis(100));
                txs = new ArrayList<>();
                for (ConsumerRecord<String, Transaction> record : records) {
                    System.out.printf("----------------------------------------------------------------------\n");
                    txs.add(record.value());
                    System.out.printf("offset = %d, key = %s, value = %s partition= %d\n", record.offset(), record.key(), record.value(), record.partition());
                    if (txs.size() >= BLOCK_TRANSACTIONS) {
                        //create new block
                        bk = new Block();
                        bk.setNonce(index);
                        bk.setTimestamp(new Timestamp(System.currentTimeMillis()));
                        bk.setTransactions(txs);
                        bk.setPreviousHash(blockhash);

                        //Proof of Stake get the validator
                        blockValidator = pp.getBlockValidator();
                        //Proof of Stake Validate the new block
                        if (blockValidator.validateBlock(blockhash, bk) == true) {
                            // Add the new block to the chain
                            producer = new KafkaProducer<String, Block>(proBlock);
                            recordBlock = new ProducerRecord<String, Block>(BLOCKCHAIN_TOPIC, Integer.toString(index), bk);
                            producer.send(recordBlock, (recordMetadata, e) -> {
                                if (e == null) {
                                    System.out.println("--->New block created!<---");
                                    System.out.println(recordMetadata.toString());
                                } else {
                                    System.out.println("--->Error..!!!<---");
                                    e.printStackTrace();
                                }
                            });
                            producer.flush();
                            producer.close();
                            index++;
                            txs = new ArrayList<>();
                            blockhash = bk.getHash();
                        } else {
                            System.out.println("--->Block not added...!!!<---");
                            //Validator is removed from the poll due foul play and lose all its tokens.
                            pp.getValidators().remove(blockValidator);
                            System.out.printf("Validator :%s removed from the pool", blockValidator.getName());
                        }
                    }
                    System.out.printf("----------------------------------------------------------------------\n");
                }
            }
        } catch (Exception e) {
            System.out.println("-------------------------------------------------------------------------------------------------------");
            e.printStackTrace();
            System.out.println("-------------------------------------------------------------------------------------------------------");
        } finally {
            if (consumer != null)
                consumer.close();
        }
    }
}