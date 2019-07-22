package com.example.paymentservice

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component


@Component
class NotificationSource(private val source: Source) {

    fun publishPaymentSuccess(email: String, message: String) {
        val noti = Notification(email, message)
        LOGGER.info("email: {}, message: {}", email, message)
        source.output().send(MessageBuilder.withPayload(noti).build())
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(NotificationSource::class.java)
    }
}