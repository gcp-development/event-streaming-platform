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
import org.blockchain.Transaction;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;

public class App {
    public static void main(String[] args) {
        final String BOOTSTRAP_SERVERS = "10.104.90.93:9092";
        final String TRANSACTIONS_TOPIC = "transactions";
        final String GROUP_ID = "block-processor";
        final String BLOCKCHAIN_TOPIC = "blockchain";
        final String SCHEMA_REGISTRY = "http://schema-registry:8081";
        final int BLOCK_TRANSACTIONS = 3;
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
            blockhash = "Genesis block";
            txs = new ArrayList<>();
            while (true) {
                records = consumer.poll(Duration.ofMillis(100));
                txs = new ArrayList<>();
                for (ConsumerRecord<String, Transaction> record : records) {
                    txs.add(record.value());
                    System.out.printf("offset = %d, key = %s, value = %s partition= %d\n", record.offset(), record.key(), record.value(), record.partition());
                    if (txs.size() >= BLOCK_TRANSACTIONS) {
                        bk = new Block();
                        bk.setIndex(index);
                        bk.setTimestamp(new Timestamp(System.currentTimeMillis()));
                        bk.setProof(100);
                        bk.setTransactions(txs);
                        if (index == 0)
                            bk.setPreviousHash(blockhash);
                        else
                            bk.setPreviousHash(blockhash);
                        producer = new KafkaProducer<String, Block>(proBlock);
                        recordBlock = new ProducerRecord<String, Block>(BLOCKCHAIN_TOPIC, Integer.toString(index), bk);
                        producer.send(recordBlock, (recordMetadata, e) -> {
                            if (e == null) {
                                System.out.println("New block created!");
                                System.out.println(recordMetadata.toString());
                            } else {
                                System.out.println("Error..!!!");
                                e.printStackTrace();
                            }
                        });
                        producer.flush();
                        producer.close();
                        index++;
                        txs = new ArrayList<>();
                        MessageDigest md = MessageDigest.getInstance("SHA3-256");
                        byte[] result = md.digest(bk.toString().getBytes(StandardCharsets.UTF_8));
                        StringBuilder sb = new StringBuilder();
                        for (byte b : result) {
                            sb.append(String.format("%02x", b));
                        }
                        blockhash = sb.toString();
                    }
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