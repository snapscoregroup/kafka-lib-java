package lib;

import java.util.function.Predicate;

public interface Consumer<K, V> {
    static <K, V> Consumer<K, V> createDefault(ConsumerConfiguration consumerConfiguration) {
        return new KafkaConsumerReact<>(consumerConfiguration);
    }

    void startConsume(ConsumerListener<K, V> consumerListener);

    void startConsume(ConsumerListener<K, V> consumerListener, Predicate<V> valuePredicate);

    void close();
}
