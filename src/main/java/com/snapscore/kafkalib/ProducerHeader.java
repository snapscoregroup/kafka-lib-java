package com.snapscore.kafkalib;

public interface ProducerHeader {
    String key();

    byte[] value();
}
