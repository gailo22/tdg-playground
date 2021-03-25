package com.gailo22.kafka.kafkaproducerconsumer

import org.springframework.web.bind.annotation.RestController
import com.gailo22.kafka.kafkaproducerconsumer.model.Foo1

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.beans.factory.annotation.Autowired

@RestController
class Controller {

    @Autowired
    private val template: KafkaTemplate<Any, Any>? = null

    @PostMapping(path = ["/send/foo/{what}"])
    fun sendFoo(@PathVariable what: String?) {
        template!!.send("topic1", Foo1(what!!))
    }
}