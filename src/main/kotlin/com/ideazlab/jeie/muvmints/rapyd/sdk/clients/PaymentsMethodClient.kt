package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.ListPaymentMethodsResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.PaymentRequiredFieldsResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface PaymentsMethodClient {
    @Get("/v1/payment_methods/countries/{country}{?currency}")
    fun listPaymentMethodsByCountry(
        @PathVariable country: String,
        @QueryValue("currency") currency: String? = null,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): ListPaymentMethodsResponse

    @Get("/v1/payment_methods/{type}/required_fields")
    fun getPaymentTypeRequiredFields(
        @PathVariable type: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): PaymentRequiredFieldsResponse
}