package lib;

import java.util.List;

/**
 * @param bootstrapServer server ip address with port like 127.0.0.1:9092
 * @param topics          list of topics to listen
 */
public record ConsumerConfiguration(String bootstrapServer, List<String> topics) {

    public ConsumerConfiguration {
        if (bootstrapServer == null || bootstrapServer.isEmpty())
            throw new IllegalArgumentException("The bootstrapServer is null or empty");
        if (topics == null || topics.isEmpty()) throw new IllegalArgumentException("The topics are null or empty");
    }

}
