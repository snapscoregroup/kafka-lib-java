package lib;

import java.util.List;
import java.util.Map;

/**
 * @param bootstrapServers server ip address with port like 127.0.0.1:9092
 */

public final record ProducerConfiguration(Map<String, Object> config, List<String> bootstrapServers) {

    public ProducerConfiguration {
        if (bootstrapServers == null || bootstrapServers.isEmpty())
            throw new IllegalArgumentException("The bootstrapServers is null or empty");
    }
}
