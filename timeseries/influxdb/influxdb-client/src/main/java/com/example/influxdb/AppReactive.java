package com.example.influxdb;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.reactive.InfluxDBClientReactive;
import com.influxdb.client.reactive.InfluxDBClientReactiveFactory;
import com.influxdb.client.reactive.WriteReactiveApi;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import org.reactivestreams.Publisher;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AppReactive {

    static String token = System.getenv("INFLUX_TOKEN");
    static String bucket = "noti-bucket";
    static String org = "my-org";

    public static void main(final String[] args) throws InterruptedException {

        InfluxDBClientReactive influxDBClient = InfluxDBClientReactiveFactory.create("http://localhost:8086", token.toCharArray(), org, bucket);
        WriteReactiveApi writeApi = influxDBClient.getWriteReactiveApi();

        Flowable<Notification> measurements = Flowable.interval(1, TimeUnit.MILLISECONDS)
                .map(time -> {
                    Notification noti = new Notification();
                    noti.jobID = "894463233";
                    noti.ssoid = "85414677";
                    noti.deviceID = "628714dae194cfdd";
                    noti.appID = "212";
                    noti.appVersion = "3.14.1.6";
                    noti.status = "SUCCESS";
                    noti.time = Instant.now();
                    return noti;
                });

        Publisher<WriteReactiveApi.Success> publisher = writeApi.writeMeasurements(WritePrecision.NS, measurements);
        Disposable subscriber = Flowable.fromPublisher(publisher)
                .subscribe(success -> System.out.println("Successfully written notifications"));

        Thread.sleep(900_000);
        subscriber.dispose();
        influxDBClient.close();
    }

}

