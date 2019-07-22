package com.example.paymentservice

import org.springframework.stereotype.Service

@Service
class PaymentService(private val notificationSource: NotificationSource) {

    fun pay(payment: Payment): Payment {
        //do business logic
        notificationPaymentSuccess(payment.email, "payment success message")
        return payment.copy(paymentStatus = "succcess")
    }

    private fun notificationPaymentSuccess(email: String, message: String) {
        notificationSource.publishPaymentSuccess(email, message)
    }
}
