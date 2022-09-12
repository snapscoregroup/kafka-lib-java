package lib;

public interface ConsumerListener<K, V> {
    void onConsume(ConsumerDataRecord<K, V> consumerDataRecord);
}
