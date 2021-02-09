package com.gailo22.oauth2.authserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthServerApplication

fun main(args: Array<String>) {
	runApplication<AuthServerApplication>(*args)
}
