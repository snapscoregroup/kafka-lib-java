package lib;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> consumerProps = new HashMap<>();
        final String topicName = "state-changes";
        final String groupID = "cg-snaptech-dev";
        String bootstrapServer = "eh-ne-prod-ds-streams-emop.servicebus.windows.net:9093";

        
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


        Consumer<Integer, String> kafkaConsumerReact = Consumer.createDefault(new ConsumerConfiguration(consumerProps, List.of(topicName)));

        kafkaConsumerReact.startConsume(consumerDataRecord -> System.out.println("Key: " + consumerDataRecord.key() + " value: " + consumerDataRecord.value()), s -> s.contains("event_id"));

        //Producer definition
        /*
        Producer<Integer, String> producer = Producer.createDefault(new ProducerConfiguration(List.of(bootstrapServer)));

        final List<ProducerDataRecord<Integer, String>> records = List.of(new ProducerDataRecord<>(topicName, 0, 1, "Test 1", 1L),
                new ProducerDataRecord<>(topicName, 0, 2, "Test 2", 2L));

        producer.send(records.stream());
        *
         */


//        producer.close();
//        kafkaConsumerReact.close();
    }

}
