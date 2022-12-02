package com.example.influxdb;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

public class App {

    public static void main(final String[] args) {

        // You can generate an API token from the "API Tokens Tab" in the UI
        String token = System.getenv("INFLUX_TOKEN");
        String bucket = "noti-bucket";
        String org = "my-org";

        InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());

        List<Notification> measurements = generateData(5000);

        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        writeApi.writeMeasurements(bucket, org, WritePrecision.NS, measurements);

//        String query = "from(bucket: \"sample-data\") |> range(start: -1h)";
//        List<FluxTable> tables = client.getQueryApi().query(query, org);
//
//        for (FluxTable table : tables) {
//            for (FluxRecord record : table.getRecords()) {
//                System.out.println(record);
//            }
//        }

        client.close();

    }

    private static List<Notification> generateData(int size) {
        List<Notification> results = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Notification noti = new Notification();
            noti.jobID = "894463233";
            noti.ssoid = "85414677";
            noti.deviceID = "628714dae194cf89";
            noti.appID = "212";
            noti.appVersion = "3.14.1.6";
            noti.status = "SUCCESS";
            noti.time = Instant.now();
            results.add(noti);
        }
        return results;
    }

}

