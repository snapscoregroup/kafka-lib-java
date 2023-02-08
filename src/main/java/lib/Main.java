package lib;

import lib.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> consumerProps = new HashMap<>();
        final String topicName = "state-changes";
        final String groupID = "cg-snaptech-prod";
        String bootstrapServer = "127.0.0.1:9092";

        final String pwd = "{FULL CONNECTION STRING}"; // add full connection string emop
        consumerProps.put(ConsumerConfigurationProperties.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        consumerProps.put(ConsumerConfigurationProperties.GROUP_ID_CONFIG, groupID);
        consumerProps.put(ConsumerConfigurationProperties.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(ConsumerConfigurationProperties.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfigurationProperties.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // SASL config
//        consumerProps.put("security.protocol", "SASL_SSL");
//        consumerProps.put("sasl.mechanism", "PLAIN");
//        consumerProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"" + pwd + "\";");

        /*
        Consumer<Integer, String> kafkaConsumerReact = Consumer.createDefault(new ConsumerConfiguration(consumerProps, List.of(topicName)));

        kafkaConsumerReact.startConsume(consumerDataRecord -> {
                    System.out.println(consumerDataRecord.value());
                },
                s -> s.contains("event_id") && s.contains("price update"));

         */

        //Producer definition
        Map<String, Object> prodProps = new HashMap<>();
        prodProps.put(ProducerConfigurationProperties.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        prodProps.put(ProducerConfigurationProperties.GROUP_ID_CONFIG, groupID);
        prodProps.put(ProducerConfigurationProperties.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        prodProps.put(ProducerConfigurationProperties.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        Producer<Integer, String> producer = Producer.createDefault(new ProducerConfiguration(prodProps));

        List<ProducerHeader> headers = List.of(new ProducerDataRecordHeader("retention.ms", "1000".getBytes(StandardCharsets.UTF_8)));
        final List<ProducerDataRecord<Integer, String>> records = List.of(new ProducerDataRecord<>("Testretention", 0, 1, "Test 1", 1L, headers),
                new ProducerDataRecord<>(topicName, 0, 2, "Test 2", 2L, List.of()));

        producer.send(records.stream());


        Thread.sleep(5000);
        producer.close();
        /// kafkaConsumerReact.close();
    }

}
