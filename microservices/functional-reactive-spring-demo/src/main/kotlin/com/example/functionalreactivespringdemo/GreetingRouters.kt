package com.example.functionalreactivespringdemo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class GreetingRouters(private val handler: GreetingHandler) {

    @Bean
    fun route() = router {
        GET("/hi", handler::sayHi)
        GET("/hello", handler::sayHello)
        GET("/hello/{name}", handler::sayHelloName)
    }
}