package lib;

import java.util.Map;

public final record ProducerConfiguration(Map<String, Object> config) {

    public ProducerConfiguration {
        if (config == null || config.isEmpty())
            throw new IllegalArgumentException("The producer config is null or empty");
    }
}
