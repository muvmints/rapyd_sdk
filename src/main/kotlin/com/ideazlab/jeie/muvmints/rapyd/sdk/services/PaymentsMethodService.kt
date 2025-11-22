package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.ListPaymentMethodsResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.PaymentsMethodClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
@Requires(bean = RapydConfig::class)
class PaymentsMethodService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: PaymentsMethodClient
): BaseService(){
    fun listPaymentMethodsByCountry(country: String, currency: String? = null): ListPaymentMethodsResponse {
        val path = buildString {
            append("/v1/payment_methods/countries/${country.lowercase()}")
            if (!currency.isNullOrBlank()) append("?currency=$currency")
        }
        val signed = sign("get", path, null,config)
        return client.listPaymentMethodsByCountry(
            country = country.lowercase(),
            currency = currency,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getRequiredFieldsForType(type: String): Any {
        val path = buildString {
            append("/v1/payment_methods/$type/required_fields")
        }
        val signed = sign("get", path, null,config)
        return client.getPaymentTypeRequiredFields(
            type=type,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }
}