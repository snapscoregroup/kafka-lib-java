package lib;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final String topicName = "test";
        final String bootstrapServer = "127.0.0.1:9092";

        //Consumer definition
        Consumer<Integer, String> kafkaConsumerReact = Consumer.createDefault(new ConsumerConfiguration(bootstrapServer, List.of(topicName)));

        kafkaConsumerReact.startConsume(new ConsumerListener<Integer, String>() {
            @Override
            public void onConsume(ConsumerDataRecord<Integer, String> consumerDataRecord) {
                System.out.println("Key: " + consumerDataRecord.key() + " value: " + consumerDataRecord.value());
            }
        });

        //Producer definition
        Producer<Integer, String> producer = Producer.createDefault(new ProducerConfiguration(List.of(bootstrapServer)));

        final List<ProducerDataRecord<Integer, String>> records = List.of(new ProducerDataRecord<>(topicName, 0, 1, "Test 1", 1L),
                new ProducerDataRecord<>(topicName, 0, 2, "Test 2", 2L));

        producer.send(records.stream());

        Thread.sleep(2000);

        producer.close();
        kafkaConsumerReact.close();
    }

}
