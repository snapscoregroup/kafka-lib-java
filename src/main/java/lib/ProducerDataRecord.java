package lib;

/**
 * Represents an outgoing record. Along with the record to send to Kafka.
 * Type parameters:
 *
 * @param topic     Topic to which record is sent
 * @param partition The partition to which the record is sent. If null, the partitioner configured
 *                  for the {@link Producer} will be used to choose the partition.
 * @param key       <K> – Outgoing record key type
 * @param value     <V> – Outgoing record value type
 * @param timestamp The timestamp of the record. If null, the current timestamp will be assigned by the producer.
 * @param headers   The headers can contain another information's
 */
public record ProducerDataRecord<K, V>(String topic, Integer partition, K key, V value, Long timestamp,
                                       Iterable<ProducerHeader> headers) {

    ProducerDataRecord(String topic, Integer partition, K key, V value, Long timestamp) {
        this(topic, partition, key, value, timestamp, null);
    }
}
