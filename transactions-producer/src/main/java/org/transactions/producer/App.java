package org.transactions.producer;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.blockchain.Transaction;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
public class App {

    public static void main(String[] args) {
        final String BOOTSTRAP_SERVERS = "10.104.90.93:9092";
        final String TOPIC_NAME = "transactions";
        final String SCHEMA_REGISTRY = "http://schema-registry:8081";
        final int MAX_RANGE = 2000;
        final int MIN_RANGE = 1;

        Properties pro;
        KafkaProducer<String, Transaction> producer = null;
        ProducerRecord<String, Transaction> record;
        Transaction tx;
        int randomNum;

        try {
            pro = new Properties();
            pro.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            pro.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            pro.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class.getName());
            pro.setProperty(KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY);
            System.out.printf("\n\n");
            for (int i = 0; i < 15; i++) {
                randomNum = ThreadLocalRandom.current().nextInt(MIN_RANGE, MAX_RANGE + 1);
                tx = new Transaction(randomNum, "RECIPIENTQGefi2DMPTfTL5SLmv7DivfN" + Integer.toString(i), "SENDEReP5QGefi2DMPTfTL5SLmv7DivfN" + Integer.toString(i));
                producer = new KafkaProducer<String, Transaction>(pro);
                record = new ProducerRecord<String, Transaction>(TOPIC_NAME, Integer.toString(i), tx);
                System.out.printf("Amount = %d, Recipient = %s, Sender= %s \n", tx.getAmount(), tx.getRecipient(), tx.getSender());
                producer.send(record, (recordMetadata, e) -> {
                    System.out.printf("----------------------------------------------------------------------\n");
                    if (e == null) {
                        System.out.println("Success...Transaction inserted!");
                        System.out.println();
                        System.out.printf("Topic:%s Partition:%d Offset:%d\n", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
                    } else {
                        System.out.println("Error...Transaction not inserted!");
                        e.printStackTrace();
                    }
                    System.out.printf("----------------------------------------------------------------------\n");
                });
                producer.flush();
                producer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (producer != null)
                producer.close();
        }
    }
}