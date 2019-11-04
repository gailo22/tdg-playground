package com.example.functionalreactivespringdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = arrayOf(MongoReactiveDataAutoConfiguration::class))
class FunctionalReactiveSpringDemoApplication

fun main(args: Array<String>) {
	runApplication<FunctionalReactiveSpringDemoApplication>(*args)
}
