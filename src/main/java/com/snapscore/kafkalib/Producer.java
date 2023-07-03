package com.snapscore.kafkalib;

import java.util.List;

public interface Producer<K, V> {
    static <K, V> Producer<K, V> createDefault(ProducerConfiguration producerConfiguration) {
        return new KafkaProducerReact<>(producerConfiguration);
    }

    void send(ProducerDataRecord<K, V> producerDataRecord);

    void send(List<ProducerDataRecord<K, V>> dataRecords);

    void close();

}
