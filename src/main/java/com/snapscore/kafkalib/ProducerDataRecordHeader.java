package com.snapscore.kafkalib;

public record ProducerDataRecordHeader(String key, byte[] value) implements ProducerHeader {
}
