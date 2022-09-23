package lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> consumerProps = new HashMap<>();
        final String topicName = "";

        //Consumer definition

        Consumer<Integer, String> kafkaConsumerReact = Consumer.createDefault(new ConsumerConfiguration(consumerProps, List.of(topicName)));

        kafkaConsumerReact.startConsume(new ConsumerListener<Integer, String>() {
            @Override
            public void onConsume(ConsumerDataRecord<Integer, String> consumerDataRecord) {
                System.out.println("Key: " + consumerDataRecord.key() + " value: " + consumerDataRecord.value());
            }
        });

        //Producer definition
        /*
        Producer<Integer, String> producer = Producer.createDefault(new ProducerConfiguration(List.of(bootstrapServer)));

        final List<ProducerDataRecord<Integer, String>> records = List.of(new ProducerDataRecord<>(topicName, 0, 1, "Test 1", 1L),
                new ProducerDataRecord<>(topicName, 0, 2, "Test 2", 2L));

        producer.send(records.stream());
        *
         */

        //Thread.sleep(20000);

        //producer.close();
//        kafkaConsumerReact.close();
    }

}
