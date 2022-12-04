package org.example;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.blockchain.Transaction;

import java.util.Properties;

public class App {
    public static void main(String[] args) {
        final String BOOTSTRAP_SERVERS = "10.97.176.6:9092";
        final String TOPIC_NAME = "test-topic";
        Properties pro;
        KafkaProducer<String, Transaction> producer= null;
        ProducerRecord<String, Transaction> record;

        try {
            pro = new Properties();
            pro.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            pro.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            //pro.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            pro.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class.getName());
            pro.setProperty(KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,"http://Schema-registry:8081");

            Transaction tx = new Transaction(10, "RECIPIENTQGefi2DMPTfTL5SLmv7DivfNa","SENDEReP5QGefi2DMPTfTL5SLmv7DivfNa");

            producer = new KafkaProducer<String, Transaction>(pro);
            record = new ProducerRecord<String, Transaction>(TOPIC_NAME, "3", tx);
            producer.send(record);
            producer.flush();
            producer.close();

            /*
            for (int i = 31; i <= 60; i++) {
                producer = new KafkaProducer<String, String>(pro);
                record = new ProducerRecord<String, String>(TOPIC_NAME, Integer.toString(i), "test" + i);
                producer.send(record);
                producer.flush();
                producer.close();
            }*/
            System.out.println("Message batch Sent...!!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (producer != null)
                producer.close();
        }
    }
}