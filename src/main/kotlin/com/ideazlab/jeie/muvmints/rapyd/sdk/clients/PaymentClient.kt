package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydCreatePaymentRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CreatePaymentResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface PaymentClient {
    @Post("/v1/payments")
    fun createPayment(
        @Body body: RapydCreatePaymentRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): CreatePaymentResponse
}