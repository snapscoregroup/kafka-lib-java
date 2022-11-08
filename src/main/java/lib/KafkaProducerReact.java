package lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.stream.Stream;

public final class KafkaProducerReact<K, V> implements Producer<K, V> {
    public static final Logger log = LoggerFactory.getLogger(KafkaProducerReact.class);

    private final KafkaSender<K, V> sender;

    public KafkaProducerReact(ProducerConfiguration producerConfiguration) {
        SenderOptions<K, V> senderOptions =
                SenderOptions.<K, V>create(producerConfiguration.config())
                        .maxInFlight(1024);

        sender = KafkaSender.create(senderOptions);
    }

    @Override
    public void send(ProducerDataRecord<K, V> producerDataRecord) {
        final SenderRecord<K, V, K> record = SenderRecord.create(producerDataRecord.topic(), producerDataRecord.partition(), producerDataRecord.timestamp(), producerDataRecord.key(), producerDataRecord.value(), producerDataRecord.key());

        final Flux<SenderRecord<K, V, K>> tFlux = Flux.just(record);

        sender.send(tFlux)
                .doOnError(Throwable::printStackTrace)
                .doOnNext(r -> log.info("Message send response: topic {}, offset {}", r.recordMetadata().topic(), r.recordMetadata().offset()))
                .subscribe();
    }

    @Override
    public void send(Stream<ProducerDataRecord<K, V>> dataRecordStream) {
        final Flux<SenderRecord<K, V, K>> senderRecordFlux = Flux.fromStream(dataRecordStream)
                .map(producerDataRecord -> SenderRecord.create(producerDataRecord.topic(), producerDataRecord.partition(), producerDataRecord.timestamp(), producerDataRecord.key(), producerDataRecord.value(), producerDataRecord.key()));

        sender.send(senderRecordFlux)
                .doOnError(Throwable::printStackTrace)
                .doOnNext(r -> log.info("Message send response: topic {}, offset {}", r.recordMetadata().topic(), r.recordMetadata().offset()))
                .subscribe();
    }

    @Override
    public void close() {
        sender.close();
    }
}
