package com.example.kafa.activecallingapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.HostInfo;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import static com.example.kafa.activecallingapp.Constants.ACTIVE_CALLING_TOPIC;
import static com.example.kafa.activecallingapp.Constants.ACTIVE_CALLING_STORE;


@EnableKafka
@EnableKafkaStreams
@SpringBootApplication
@Slf4j
public class ActiveCallingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActiveCallingApplication.class, args);
    }

    @Bean
    NewTopic activeCallingTopic() {
        return TopicBuilder.name("active-calling").partitions(6).replicas(1).build();
    }

    @LocalRunningGrpcPort
    private int port;

    @Bean
    HostInfo getHostInfo() throws UnknownHostException {
        return new HostInfo(InetAddress.getLocalHost().getHostName(), port);
    }

    @Bean
    KafkaStreamsConfiguration defaultKafkaStreamsConfig(KafkaProperties configuredKafkaProperties)
            throws UnknownHostException {
        Map<String, Object> newConfig = configuredKafkaProperties.buildStreamsProperties();
        newConfig.put(StreamsConfig.APPLICATION_SERVER_CONFIG, InetAddress.getLocalHost().getHostName() + ":" + port);
        log.info(String.format("=> %s:%d", InetAddress.getLocalHost().getHostName(), port));
        return new KafkaStreamsConfiguration(newConfig);
    }

}

@Component
class ActiveCallingView {

    @Autowired
    public void buildActiveCallingView(StreamsBuilder builder) {
        builder.table(ACTIVE_CALLING_TOPIC,
                Consumed.with(Serdes.String(), Serdes.String()),
                Materialized.as(ACTIVE_CALLING_STORE));
    }
}

