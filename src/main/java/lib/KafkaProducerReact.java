package lib;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class KafkaProducerReact<K, V> implements Producer<K, V> {
    private final KafkaSender<K, V> sender;

    public KafkaProducerReact(ProducerConfiguration producerConfiguration) {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerConfiguration.bootstrapServers());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        SenderOptions<K, V> senderOptions =
                SenderOptions.<K, V>create(producerProps)
                        .maxInFlight(1024);

        sender = KafkaSender.create(senderOptions);
    }

    @Override
    public void send(ProducerDataRecord<K, V> producerDataRecord) {
        final SenderRecord<K, V, K> record = SenderRecord.create(producerDataRecord.topic(), producerDataRecord.partition(), producerDataRecord.timestamp(), producerDataRecord.key(), producerDataRecord.value(), producerDataRecord.key());

        final Flux<SenderRecord<K, V, K>> tFlux = Flux.just(record);

        sender.send(tFlux)
                .doOnError(Throwable::printStackTrace)
                .doOnNext(r -> System.out.printf("Message #%d send response: %s\n", r.correlationMetadata(), r.recordMetadata()))
                .subscribe();
    }

    @Override
    public void send(Stream<ProducerDataRecord<K, V>> dataRecordStream) {
        final Flux<SenderRecord<K, V, K>> senderRecordFlux = Flux.fromStream(dataRecordStream)
                .map(producerDataRecord -> SenderRecord.create(producerDataRecord.topic(), producerDataRecord.partition(), producerDataRecord.timestamp(), producerDataRecord.key(), producerDataRecord.value(), producerDataRecord.key()));

        sender.send(senderRecordFlux)
                .doOnError(Throwable::printStackTrace)
                .doOnNext(r -> System.out.printf("Message #%d send response: %s\n", r.correlationMetadata(), r.recordMetadata()))
                .subscribe();
    }

    @Override
    public void close() {
        sender.close();
    }
}
