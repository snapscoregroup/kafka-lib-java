package lib;

/**
 * Represents an outgoing record. Along with the record to send to Kafka.
 * Type parameters:
 * <K> – Outgoing record key type
 * <V> – Outgoing record value type
 */
public final record ProducerDataRecord<K, V>(String topic, Integer partition, K key, V value, Long timestamp) {
}
