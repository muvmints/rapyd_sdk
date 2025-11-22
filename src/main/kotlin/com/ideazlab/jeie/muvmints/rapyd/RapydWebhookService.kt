package com.ideazlab.jeie.muvmints.rapyd

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.dto.RapydWebhookEnvelope
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.utils.RapydSignatureUtil
import io.micronaut.http.HttpRequest
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class RapydWebhookService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun handleWebhook(
        request: HttpRequest<Any>,
        signatureHeader: String,
        salt: String,
        timestamp: Long,
        rawBody: String
    ) {
        val urlPath = request.uri.toString()

        val calculated = RapydSignatureUtil.signWebhook(
            urlPath = urlPath,
            bodyJson = rawBody,
            accessKey = config.accessKey,
            secretKey = config.secretKey,
            salt = salt,
            timestamp = timestamp
        )

        if (calculated != signatureHeader) {
            log.warn("Invalid Rapyd webhook signature. Expected=\$calculated, actual=\$signatureHeader")
            throw IllegalStateException("Invalid Rapyd webhook signature")
        }

        val event = objectMapper.readValue(rawBody, RapydWebhookEnvelope::class.java)

        log.info("Rapyd webhook received: type=\${event.type}, data.id=\${event.data?.id}, status=\${event.data?.status}")

        when (event.type) {
            "PAYMENT_COMPLETED" -> handlePaymentCompleted(event)
            "PAYMENT_FAILED"    -> handlePaymentFailed(event)
            else -> log.info("Unhandled Rapyd webhook type: \${event.type}")
        }
    }

    private fun handlePaymentCompleted(event: RapydWebhookEnvelope) {
        log.info("Payment completed: \${event.data?.id} amount=\${event.data?.amount} \${event.data?.currency}")
    }

    private fun handlePaymentFailed(event: RapydWebhookEnvelope) {
        log.info("Payment failed: \${event.data?.id}")
    }
}