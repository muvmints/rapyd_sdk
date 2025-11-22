package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydCreatePaymentRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CreatePaymentResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.PaymentClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
@Requires(bean = RapydConfig::class)
class PaymentService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: PaymentClient
): BaseService(){
    fun createPayment(body: RapydCreatePaymentRequest): CreatePaymentResponse {
        val path = "/v1/payments"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createPayment(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }


    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }
}