package org.example;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.blockchain.Transaction;

public class App {
    public static void main(String[] args) {
        final String BOOTSTRAP_SERVERS = "10.104.90.93:9092";
        final String TOPIC_NAME = "test-topic";
        //final String TOPIC_NAME = "/sample-stream:json-schema_example";
        Properties pro;
        KafkaConsumer<String, Transaction> consumer = null;

        try {
            pro = new Properties();
            pro.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            pro.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
            pro.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            //pro.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            pro.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonSchemaDeserializer.class.getName());
            pro.setProperty(KafkaJsonSchemaDeserializerConfig.JSON_VALUE_TYPE, Transaction.class.getName());


            pro.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            pro.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            pro.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");

            pro.setProperty(KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://Schema-registry:8081");


            //consumer = new KafkaConsumer<String, String>(pro);
            consumer = new KafkaConsumer<String, Transaction>(pro);
            //consumer.subscribe(Arrays.asList(TOPIC_NAME));
            consumer.subscribe(Collections.singletonList(TOPIC_NAME));

            System.out.println("Listening...");

            while (true) {

                ConsumerRecords<String, Transaction> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    Transaction tx = record.value();
                    System.out.printf("%s %d %d %s \n", record.topic(), record.partition(), record.offset(), tx);
                    System.out.printf("%s \n", tx.getAmount());
                    System.out.printf("%s \n", tx.getSender());
                    System.out.printf("%s \n", tx.getRecipient());

                });


                /*
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();

                    System.out.println((key != null ? "key=" + key + ", " : "") + "value=" + value);
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (consumer != null)
                consumer.close();
        }
    }
}