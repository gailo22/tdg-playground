package com.example.kafa.activecallingapp;

import com.example.kafa.activecallingapp.proto.ActiveCallingQueryGrpc;
import com.example.kafa.activecallingapp.proto.ActiveCallingResponse;
import com.example.kafa.activecallingapp.proto.ActiveCallingServiceRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.example.kafa.activecallingapp.Constants.ACTIVE_CALLING_STORE;

@RestController
@RequiredArgsConstructor
public class ActiveCallingController {

    @LocalRunningGrpcPort
    private int port;

    @GetMapping("/calling/{ssoid}")
    public ActiveCallingStatus getActiveCalling2(@PathVariable final String ssoid) throws UnknownHostException {
        ActiveCallingServiceRequest request = ActiveCallingServiceRequest.newBuilder()
                .setKey(ssoid)
                .setStoreName(ACTIVE_CALLING_STORE)
                .build();
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(InetAddress.getLocalHost().getHostName(), port)
                .usePlaintext()
                .build();
        final ActiveCallingQueryGrpc.ActiveCallingQueryBlockingStub stub = ActiveCallingQueryGrpc.newBlockingStub(channel);
        ActiveCallingResponse callingResponse = stub.getActiveCalling(request);
        channel.shutdown();

        return new ActiveCallingStatus(ssoid, Boolean.parseBoolean(callingResponse.getStatus()));
    }
}
