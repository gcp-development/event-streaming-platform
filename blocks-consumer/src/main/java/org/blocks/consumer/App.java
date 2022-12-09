package org.blocks.consumer;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.blockchain.Block;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        final String BOOTSTRAP_SERVERS = "10.104.90.93:9092";
        final String TOPIC_NAME = "blockchain";
        final String SCHEMA_REGISTRY = "http://schema-registry:8081";

        Properties pro;
        KafkaConsumer<String, Block> consumer = null;
        ConsumerRecords<String, Block> records;

        try {
            pro = new Properties();
            pro.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            pro.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
            pro.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            pro.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonSchemaDeserializer.class.getName());
            pro.setProperty(KafkaJsonSchemaDeserializerConfig.JSON_VALUE_TYPE, Block.class.getName());
            pro.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            pro.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            pro.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
            pro.setProperty(KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY);
            consumer = new KafkaConsumer<String, Block>(pro);
            consumer.subscribe(Collections.singletonList(TOPIC_NAME));
            System.out.println("Listening...");
            consumer.poll(0);
            consumer.seekToBeginning(consumer.assignment());
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            while (true) {
                records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    Block bk = record.value();
                    System.out.printf("%s %d %d %s \n", record.topic(), record.partition(), record.offset(), bk.toString());
                    System.out.printf("----------------------------------------------------------------------\n");
                    try {
                        System.out.printf("Block Hash:%s \n", bk.getHash());
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("Index:%s \n", bk.getIndex());
                    System.out.printf("Timestamp:%s \n", bk.getTimestamp());
                    System.out.printf("Proof:%s \n", bk.getProof());
                    System.out.printf("PreviousHash:%s \n", bk.getPreviousHash());
                    System.out.printf("Tx Size:%s \n", bk.getTransactions().size());
                    System.out.printf("----------------------------------------------------------------------\n");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (consumer != null)
                consumer.close();
        }
    }
}