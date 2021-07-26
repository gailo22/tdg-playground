package streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.common.utils.Bytes;

import java.util.Arrays;
import java.util.Properties;

import static org.apache.kafka.streams.state.QueryableStoreTypes.keyValueStore;
import static org.apache.kafka.streams.StoreQueryParameters.fromNameAndType;

public class StateStoreExample {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "CallStoreConsumerApplication");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        builder.table("call-store-topic",
                Consumed.with(Serdes.String(), Serdes.String()),
                Materialized.as("call-store"));

        Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();

//        ReadOnlyKeyValueStore<String, String> store =
//                streams.store(fromNameAndType("call-store", keyValueStore()));

//        try {
//            String key = "123456";
//            String value = store.get(key);
//            System.out.println(String.format("key -> value: %s -> %s", key, value));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                streams.close();
            } catch (final Exception e) {
                // ignored
            }
        }));
    }
}
