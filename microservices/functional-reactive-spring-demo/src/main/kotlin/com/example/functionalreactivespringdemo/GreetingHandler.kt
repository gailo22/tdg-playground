package com.example.functionalreactivespringdemo

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class GreetingHandler {

    fun sayHi(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().body(Flux.just("hi, world"))

    fun sayHello(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().body(Flux.just("hello, world"))

    fun sayHelloName(request: ServerRequest): Mono<ServerResponse> {
        val name = request.pathVariable("name")
        return ServerResponse.ok().body(Flux.just("hello, " + name))
    }
}