package effective.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Map;

import static java.lang.System.out;

public class BasicProducerSample {
    public static void main(String[] args) throws InterruptedException {
        var topic = "getting-started";
        Map<String, Object> config =
                Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        "localhost:9092",
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                        StringSerializer.class.getName(),
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        StringSerializer.class.getName(),
                        ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
                        true);

        try (var producer = new KafkaProducer<String, String>(config)) {
            while (true) {
                var key = "myKey";
                var value = new Date().toString();
                out.format("Publishing record with value %s%n", value);

                Callback callback = ((metadata, exception) -> {
                    out.format("Publish with metadata: %s, error: %s%n", metadata, exception);
                });

                producer.send(new ProducerRecord<>(topic, key, value), callback);

                Thread.sleep(1000);
            }
        }
    }
}
