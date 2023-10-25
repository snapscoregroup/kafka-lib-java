package lib;

public class ConsumerConfigurationProperties {

    public static final String BOOTSTRAP_SERVERS_CONFIG = CommonConfigs.BOOTSTRAP_SERVERS_CONFIG;
    public static final String GROUP_ID_CONFIG = CommonConfigs.GROUP_ID_CONFIG;
    public static final String KEY_DESERIALIZER_CLASS_CONFIG = CommonConfigs.KEY_DESERIALIZER_CLASS_CONFIG;
    public static final String VALUE_DESERIALIZER_CLASS_CONFIG = CommonConfigs.VALUE_DESERIALIZER_CLASS_CONFIG;
    public static final String AUTO_OFFSET_RESET_CONFIG = "auto.offset.reset";
    public static final String REQUEST_TIMEOUT_MS_CONFIG = CommonConfigs.REQUEST_TIMEOUT_MS_CONFIG;
    public static final String SECURITY_PROTOCOL = CommonConfigs.SECURITY_PROTOCOL;
    public static final String SASL_MECHANISM = CommonConfigs.SASL_MECHANISM;
    public static final String SASL_JAAS_CONFIG = "sasl.jaas.config";
    public static final String MAX_PARTITION_FETCH_BYTES_CONFIG = "max.partition.fetch.bytes";
    public static final String FETCH_MAX_BYTES_CONFIG = "fetch.max.bytes";
    public static final String MAX_POLL_RECORDS_CONFIG = "max.poll.records";

}
