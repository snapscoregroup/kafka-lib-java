package lib;

import java.util.List;
import java.util.Map;

/**
 * @param bootstrapServer server ip address with port like 127.0.0.1:9092
 * @param topics          list of topics to listen
 */
public record ConsumerConfiguration(Map<String, Object> config, List<String> topics) {

    public ConsumerConfiguration {
        if (config == null || config.isEmpty())
            throw new IllegalArgumentException("The config is null or empty");
    }

}
