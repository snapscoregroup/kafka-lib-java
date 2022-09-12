package lib;

public interface Consumer<K, V> {
    static <K, V> Consumer<K, V> createDefault(ConsumerConfiguration consumerConfiguration) {
        return new KafkaConsumerReact<>(consumerConfiguration);
    }

    void startConsume(ConsumerListener<K, V> consumerListener);

    void close();
}
