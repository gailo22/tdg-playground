package com.example.paymentservice

import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("payment")
class PaymentController(private val paymentService: PaymentService) {

    @PostMapping("/pay")
    fun pay(@RequestBody payment: Payment): Payment {
        return paymentService.pay(payment)
    }

    @GetMapping("/hello")
    fun hello() = "hello world"
}