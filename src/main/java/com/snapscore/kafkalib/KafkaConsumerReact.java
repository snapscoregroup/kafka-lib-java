package com.snapscore.kafkalib;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverPartition;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.function.Predicate;

final class KafkaConsumerReact<K, V> implements Consumer<K, V> {
    private final KafkaReceiver<K, V> receiver;
    private Disposable disposable;

    KafkaConsumerReact(ConsumerConfiguration consumerConfiguration) {
        if (consumerConfiguration == null) throw new IllegalArgumentException("The consumer configuration is null");

        ReceiverOptions<K, V> receiverOptions = ReceiverOptions.<K, V>create(consumerConfiguration.config())
                .addAssignListener(partitions -> partitions.forEach(ReceiverPartition::seekToEnd))
                .subscription(consumerConfiguration.topics());
        receiver = KafkaReceiver.create(receiverOptions);
    }

    @Override
    public void startConsume(ConsumerListener<K, V> consumerListener) {
        Flux<ReceiverRecord<K, V>> inboundFlux = receiver.receive();

        disposable = inboundFlux
                .subscribe(r -> {
                    consumerListener.onConsume(new ConsumerDataRecord<K, V>(r.key(), r.value(), r.topic(), r.timestamp(), r.partition(), r.offset()));
                    r.receiverOffset().acknowledge();
                });

    }

    @Override
    public void startConsume(ConsumerListener<K, V> consumerListener, Predicate<V> valuePredicate) {
        Flux<ReceiverRecord<K, V>> inboundFlux = receiver.receive();


        disposable = inboundFlux
                .filter(r -> valuePredicate.test(r.value()))
                .subscribe(r -> {
                    consumerListener.onConsume(new ConsumerDataRecord<K, V>(r.key(), r.value(), r.topic(), r.timestamp(), r.partition(), r.offset()));
                    r.receiverOffset().acknowledge();
                });
    }

    @Override
    public void close() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

}
