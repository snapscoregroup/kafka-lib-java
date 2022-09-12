package lib;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerReact<K, V> implements Consumer<K, V> {
    private final KafkaReceiver<Integer, String> receiver;
    private Disposable disposable;

    public KafkaConsumerReact(ConsumerConfiguration consumerConfiguration) {
        if (consumerConfiguration == null) throw new IllegalArgumentException("The consumer configuration is null");

        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerConfiguration.bootstrapServer());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "snapscore-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        ReceiverOptions<Integer, String> receiverOptions = ReceiverOptions.<Integer, String>create(consumerProps).subscription(consumerConfiguration.topics());
        receiver = KafkaReceiver.create(receiverOptions);
    }

    @Override
    public void startConsume(ConsumerListener<K, V> consumerListener) {
        Flux<ReceiverRecord<Integer, String>> inboundFlux = receiver.receive();

        disposable = inboundFlux.subscribe(r -> {
            System.out.printf("Received message: %s\n", r);
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
