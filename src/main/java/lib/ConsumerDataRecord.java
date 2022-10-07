package lib;

/**
 * Creates a record to be received from a specified topic and partition
 *
 * @param key       The key of the record, if one exists (null is allowed)
 * @param value     The record contents
 * @param topic     The topic this record is received from
 * @param timestamp The timestamp of the record.
 * @param partition The partition of the topic this record is received from
 * @param offset    The offset of this record in the corresponding Kafka partition
 */
public final record ConsumerDataRecord<K, V>(K key, V value, String topic, long timestamp, int partition, long offset) {
}
