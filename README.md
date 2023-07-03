# kafka-lib-java

This library encapsulates publishing and consuming messages from Kafka through `reactor-kafka` in Java.

**Consumer usage:** 
```java
// Define consumer config properties
Map<String, Object> consumerProps = new HashMap<>();
consumerProps.put(ConsumerConfigurationProperties.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
consumerProps.put(ConsumerConfigurationProperties.GROUP_ID_CONFIG, groupID);
consumerProps.put(ConsumerConfigurationProperties.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
consumerProps.put(ConsumerConfigurationProperties.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
consumerProps.put(ConsumerConfigurationProperties.AUTO_OFFSET_RESET_CONFIG, "earliest");

// Create configuration object
ConsumerConfiguration config = new ConsumerConfiguration(consumerProps, List.of(topicName))

// Instantiate the default consumer
Consumer<Integer, String> consumer = Consumer.createDefault(config);

// Start consuming messages from Kafka
consumer.startConsume(
        consumerDataRecord -> System.out.println(consumerDataRecord.value()),
        s -> s.contains("event_id") && s.contains("update")
);
```

**Producer usage:**
```java
// Define producer config properties
Map<String, Object> prodProps = new HashMap<>();
prodProps.put(ProducerConfigurationProperties.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
prodProps.put(ProducerConfigurationProperties.GROUP_ID_CONFIG, groupID);
prodProps.put(ProducerConfigurationProperties.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
prodProps.put(ProducerConfigurationProperties.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

// Create configuration object
ProducerConfiguration config = new ProducerConfiguration(prodProps);

// Instantiate the default producer
Producer<Integer, String> producer = Producer.createDefault(config);

// Start publishing messages
producer.send(records);
```

More details can be found in `com.snapscore.kafkalib.examples.basic.Main`
