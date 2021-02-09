package com.gailo22.oauth2.resourceserver

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping

@RestController
class ResourceServerController {

    @GetMapping("/")
    fun index(@AuthenticationPrincipal jwt: Jwt): String? {
        return java.lang.String.format("Hello, %s!", jwt.getSubject())
    }

    @GetMapping("/message")
    fun message(): String? {
        return "secret message"
    }

    @PostMapping("/message")
    fun createMessage(@RequestBody message: String?): String? {
        return String.format("Message was created. Content: %s", message)
    }
}