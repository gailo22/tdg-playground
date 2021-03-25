package com.gailo22.kafka.kafkaproducerconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.core.task.SimpleAsyncTaskExecutor

import org.springframework.core.task.TaskExecutor
import org.springframework.util.backoff.FixedBackOff

import org.springframework.kafka.listener.DeadLetterPublishingRecoverer

import org.springframework.kafka.listener.SeekToCurrentErrorHandler

import org.springframework.kafka.core.KafkaOperations
import org.springframework.kafka.support.converter.StringJsonMessageConverter

import org.springframework.kafka.support.converter.RecordMessageConverter

import com.gailo22.kafka.kafkaproducerconsumer.model.Foo2

import org.springframework.kafka.annotation.KafkaListener
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile


@SpringBootApplication
class KafkaProducerConsumerApplication {

    private val exec: TaskExecutor = SimpleAsyncTaskExecutor()

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(KafkaProducerConsumerApplication::class.java)
    }

    @Bean
    fun errorHandler(template: KafkaOperations<Any?, Any?>?): SeekToCurrentErrorHandler? {
        return SeekToCurrentErrorHandler(
            DeadLetterPublishingRecoverer(template), FixedBackOff(1000L, 2)
        )
    }

    @Bean
    fun converter(): RecordMessageConverter? {
        return StringJsonMessageConverter()
    }

    @KafkaListener(id = "fooGroup", topics = ["topic1"])
    fun listen(foo: Foo2) {
        logger.info("Received: $foo")
        if (foo.foo.startsWith("fail")) {
            throw RuntimeException("failed")
        }
        exec.execute { println("Hit Enter to terminate...") }
    }

    @KafkaListener(id = "dltGroup", topics = ["topic1.DLT"])
    fun dltListen(`in`: String) {
        logger.info("Received from DLT: $`in`")
        exec.execute { println("Hit Enter to terminate...") }
    }

    @Bean
    fun topic(): NewTopic? {
        return NewTopic("topic1", 1, 1.toShort())
    }

    @Bean
    fun dlt(): NewTopic? {
        return NewTopic("topic1.DLT", 1, 1.toShort())
    }

    @Bean
    @Profile("default") // Don't run from test(s)
    fun runner(): ApplicationRunner? {
        return ApplicationRunner { args: ApplicationArguments? ->
            println("Hit Enter to terminate...")
            System.`in`.read()
        }
    }

}

fun main(args: Array<String>) {
    runApplication<KafkaProducerConsumerApplication>(*args)
}

