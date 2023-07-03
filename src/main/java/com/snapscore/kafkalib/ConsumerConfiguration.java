package com.snapscore.kafkalib;

import java.util.List;
import java.util.Map;

/**
 * @param topics list of topics to listen
 */
public record ConsumerConfiguration(Map<String, Object> config, List<String> topics) {

    public ConsumerConfiguration {
        if (config == null || config.isEmpty())
            throw new IllegalArgumentException("The config is null or empty");
    }

}
