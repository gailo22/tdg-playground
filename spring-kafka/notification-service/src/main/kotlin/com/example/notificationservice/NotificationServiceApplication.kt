package com.example.notificationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Sink
import com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener


@SpringBootApplication
@EnableBinding(Sink::class)
class NotificationServiceApplication {

    @StreamListener(Sink.INPUT)
    fun receivedSink(notification: Notification) {
        //do business logic
        LOGGER.info(" Received Notification: {}", notification)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(NotificationServiceApplication::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<NotificationServiceApplication>(*args)
}

