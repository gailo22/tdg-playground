package com.example.kafa.activecallingapp.query;

import com.example.kafa.activecallingapp.proto.HostStoreInfo;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyQueryMetadata;
import org.apache.kafka.streams.state.StreamsMetadata;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MetadataService {

    private final KafkaStreams streams;

    public MetadataService(final KafkaStreams streams) {
        this.streams = streams;
    }

    public List<HostStoreInfo> streamsMetadata() {
        // Get metadata for all of the instances of this Kafka Streams application
        final Collection<StreamsMetadata> metadata = streams.allMetadata();
        return mapInstancesToHostStoreInfo(metadata);
    }

    public List<HostStoreInfo> streamsMetadataForStore(final String store) {
        // Get metadata for all of the instances of this Kafka Streams application hosting the store
        final Collection<StreamsMetadata> metadata = streams.allMetadataForStore(store);
        return mapInstancesToHostStoreInfo(metadata);
    }

    public <K> HostStoreInfo streamsMetadataForStoreAndKey(final String store,
                                                           final K key,
                                                           final Serializer<K> serializer) {
        // Get metadata for the instances of this Kafka Streams application hosting the store and
        // potentially the value for key
        //final StreamsMetadata metadata = streams.metadataForKey(store, key, serializer);
        final KeyQueryMetadata metadata = streams.queryMetadataForKey(store, key, serializer);
        if (metadata == null) {
            throw new RuntimeException("NOT FOUND");
        }

        return HostStoreInfo.newBuilder()
                .setHost(metadata.activeHost().host())
                .setPort(metadata.activeHost().port())
                .build();
    }

    private List<HostStoreInfo> mapInstancesToHostStoreInfo(
            final Collection<StreamsMetadata> metadatas) {
        return metadatas.stream().map(metadata ->
                HostStoreInfo.newBuilder()
                        .setHost(metadata.host())
                        .setPort(metadata.port())
                        .addAllStoreNames(metadata.stateStoreNames()).build())
                .collect(Collectors.toList());
    }

}
