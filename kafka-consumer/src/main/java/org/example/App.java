package org.example;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class App {
    public static void main(String[] args) {
        final String BOOTSTRAP_SERVERS = "10.97.176.6:9092";
        final String TOPIC_NAME = "test-topic";
        Properties pro;
        KafkaConsumer<String, String> consumer = null;

        try {
            pro = new Properties();
            pro.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            pro.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
            pro.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            pro.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            pro.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            pro.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            pro.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");

            consumer = new KafkaConsumer<String, String>(pro);
            consumer.subscribe(Arrays.asList(TOPIC_NAME));

            System.out.println("Listening...");

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();

                    System.out.println((key != null ? "key=" + key + ", " : "") + "value=" + value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (consumer != null)
                consumer.close();
        }
    }
}