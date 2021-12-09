package com.example.kafkastreams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class App {

    /**
     * Topics:
     *   - my-input-topic
     *   - my-output-topic
     */
    public static void main(String[] args) {
        final String bootstrapServers = args.length > 0 ? args[0] : "localhost:9092";
        final Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "application-demo");
        config.put(StreamsConfig.CLIENT_ID_CONFIG, "application-demo-client");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final boolean doReset = args.length > 1 && args[1].equals("--reset");
        final KafkaStreams streams = buildStreams(config);

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        if (doReset) {
            streams.cleanUp();
        }

        startStreamsSync(streams);
    }

    static KafkaStreams buildStreams(final Properties config) {
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> input = builder.stream("my-input-topic");
        input.selectKey((key, value) -> value.split(" ")[0])
                .groupByKey()
                .count()
                .toStream()
                .to("my-output-topic", Produced.with(Serdes.String(), Serdes.Long()));

        return new KafkaStreams(builder.build(), config);
    }

    static void startStreamsSync(final KafkaStreams streams) {
        final CountDownLatch latch = new CountDownLatch(1);
        streams.setStateListener((newState, oldState) -> {
            if (oldState == KafkaStreams.State.REBALANCING && newState == KafkaStreams.State.RUNNING) {
                latch.countDown();
            }
        });

        streams.start();

        try {
            latch.await();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
