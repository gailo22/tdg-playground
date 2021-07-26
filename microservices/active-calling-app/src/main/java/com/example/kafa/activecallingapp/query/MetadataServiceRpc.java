package com.example.kafa.activecallingapp.query;

import com.example.kafa.activecallingapp.proto.*;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.logging.log4j.util.Strings;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.List;

import static org.apache.kafka.streams.StoreQueryParameters.fromNameAndType;
import static org.apache.kafka.streams.state.QueryableStoreTypes.keyValueStore;

@GRpcService
@RequiredArgsConstructor
@Slf4j
public class MetadataServiceRpc extends ActiveCallingQueryGrpc.ActiveCallingQueryImplBase {

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;
    private final HostInfo hostInfo;
    private MetadataService metadataService;

    @EventListener(ContextRefreshedEvent.class)
    public void refresh() {
        this.metadataService = new MetadataService(streamsBuilderFactoryBean.getKafkaStreams());
    }

    @Override
    public void getMetadataForStore(final ActiveCallingServiceRequest request,
                                    final StreamObserver<HostStoreInfoList> responseObserver) {

        final List<HostStoreInfo> hostStoreInfos = this.metadataService.streamsMetadataForStore(request.getStoreName());
        responseObserver.onNext(HostStoreInfoList.newBuilder().addAllMetadatas(hostStoreInfos).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllMetadata(final Empty request, final StreamObserver<HostStoreInfoList> responseObserver) {
        final List<HostStoreInfo> hostStoreInfos = this.metadataService.streamsMetadata();
        responseObserver.onNext(HostStoreInfoList.newBuilder().addAllMetadatas(hostStoreInfos).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getMetadataForStoreAndKey(final ActiveCallingServiceRequest request,
                                          final StreamObserver<HostStoreInfoList> responseObserver) {
        final HostStoreInfo metadata =
                this.metadataService.streamsMetadataForStoreAndKey(
                        request.getStoreName(),
                        request.getKey(),
                        Serdes.String().serializer());

        responseObserver.onNext(HostStoreInfoList.newBuilder().addMetadatas(metadata).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getActiveCalling(final ActiveCallingServiceRequest request, final StreamObserver<ActiveCallingResponse> responseObserver) {
        final KafkaStreams kafkaStreams = streamsBuilderFactoryBean.getKafkaStreams();
        final String storeName = request.getStoreName();
        final ReadOnlyKeyValueStore<String, String> store = kafkaStreams.store(fromNameAndType(storeName, keyValueStore()));
        final String id = request.getKey();
        final String status = store.get(id);

        final ActiveCallingResponse callingResponse;

        final HostStoreInfo hostStoreInfo =
                metadataService.streamsMetadataForStoreAndKey(storeName, id, Serdes.String().serializer());

        log.info("=> hostinfo: {}", hostStoreInfo);

        if (!thisHost(hostStoreInfo)) {
            // remote call
            log.debug("=> remote call: {}", request);
            final ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(hostStoreInfo.getHost(), hostStoreInfo.getPort())
                    .usePlaintext()
                    .build();
            final ActiveCallingQueryGrpc.ActiveCallingQueryBlockingStub stub = ActiveCallingQueryGrpc.newBlockingStub(channel);
            callingResponse = stub.getActiveCalling(request);
            channel.shutdown();
        } else {
            // local call
            log.debug("=> local call: {}", request);
            callingResponse = ActiveCallingResponse.newBuilder()
                    .setStatus(Strings.isBlank(status) ? "" : status)
                    .build();
        }

        responseObserver.onNext(callingResponse);
        responseObserver.onCompleted();
    }

    private boolean thisHost(final HostStoreInfo host) {
        return host.getHost().equals(hostInfo.host()) && host.getPort() == hostInfo.port();
    }

}
