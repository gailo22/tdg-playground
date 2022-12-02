package com.example.influxdb;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;

@Measurement(name = "noti")
public class Notification {
    @Column(tag = true)
    String jobID;
    @Column(tag = true)
    String ssoid;
    @Column(tag = true)
    String deviceID;
    @Column
    String appID;
    @Column
    String status;
    @Column
    String appVersion;
    @Column(timestamp = true)
    Instant time;
}
