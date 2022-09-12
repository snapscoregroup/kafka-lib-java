package lib;

import java.util.stream.Stream;

public interface Producer<K, V> {
    static <K, V> Producer<K, V> createDefault(ProducerConfiguration producerConfiguration) {
        return new KafkaProducerReact<>(producerConfiguration);
    }

    void send(ProducerDataRecord<K, V> producerDataRecord);

    void send(Stream<ProducerDataRecord<K, V>> dataRecordStream);

    void close();

}
