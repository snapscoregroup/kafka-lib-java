package examples.basic;

import com.snapscore.kafkalib.*;
import com.snapscore.kafkalib.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final String topicName = "topic-name";
        final String groupID = "group-id";
        String bootstrapServer = "127.0.0.1:9092";

        // Consumer definition
        Consumer<Integer, String> kafkaConsumerReact = Main.createConsumer(bootstrapServer, groupID, topicName);

        kafkaConsumerReact.startConsume(
                consumerDataRecord -> System.out.println(consumerDataRecord.value()),
                s -> s.contains("event_id") && s.contains("price update")
        );

        //Producer definition
        Producer<Integer, String> producer = Main.createProducer(bootstrapServer, groupID);

        final List<ProducerDataRecord<Integer, String>> records = List.of(new ProducerDataRecord<>("Testretention", 0, 1, "Test 100", 1L, List.of()),
                new ProducerDataRecord<>(topicName, 0, 2, "Test 2", 2L, List.of()));

        producer.send(records);

        Thread.sleep(5000);

        producer.close();
        kafkaConsumerReact.close();
    }

    private static Consumer<Integer, String> createConsumer(String bootstrapServer, String groupID, String topicName) {
        Map<String, Object> consumerProps = new HashMap<>();
        final String pwd = "{FULL CONNECTION STRING}"; // add full connection string emop
        consumerProps.put(ConsumerConfigurationProperties.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        consumerProps.put(ConsumerConfigurationProperties.GROUP_ID_CONFIG, groupID);
        consumerProps.put(ConsumerConfigurationProperties.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(ConsumerConfigurationProperties.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfigurationProperties.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // SASL config
        consumerProps.put("security.protocol", "SASL_SSL");
        consumerProps.put("sasl.mechanism", "PLAIN");
        consumerProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"" + pwd + "\";");

        return Consumer.createDefault(new ConsumerConfiguration(consumerProps, List.of(topicName)));
    }

    private static Producer<Integer, String> createProducer(String bootstrapServer, String groupID) {
        Map<String, Object> prodProps = new HashMap<>();
        prodProps.put(ProducerConfigurationProperties.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        prodProps.put(ProducerConfigurationProperties.GROUP_ID_CONFIG, groupID);
        prodProps.put(ProducerConfigurationProperties.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        prodProps.put(ProducerConfigurationProperties.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return Producer.createDefault(new ProducerConfiguration(prodProps));
    }

}
