package lib;

public final record ConsumerDataRecord<K, V>(K key, V value, String topic, long timestamp) {
}
