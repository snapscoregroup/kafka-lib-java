package lib;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

public class KafkaConsumerReact<K, V> implements Consumer<K, V> {
    private final KafkaReceiver<Integer, String> receiver;
    private Disposable disposable;

    public KafkaConsumerReact(ConsumerConfiguration consumerConfiguration) {
        if (consumerConfiguration == null) throw new IllegalArgumentException("The consumer configuration is null");

        ReceiverOptions<Integer, String> receiverOptions = ReceiverOptions.<Integer, String>create(consumerConfiguration.config()).subscription(consumerConfiguration.topics());
        receiver = KafkaReceiver.create(receiverOptions);
    }

    @Override
    public void startConsume(ConsumerListener<K, V> consumerListener) {
        Flux<ReceiverRecord<Integer, String>> inboundFlux = receiver.receive();

        disposable = inboundFlux
                .subscribe(r -> {
//                    System.out.printf("Received message: %s\n", r);
                    consumerListener.onConsume(new ConsumerDataRecord(r.key(), r.value(), r.topic(), r.timestamp()));
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
