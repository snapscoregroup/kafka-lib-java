package lib;

public record ProducerDataRecordHeader(String key, byte[] value) implements ProducerHeader {
}
