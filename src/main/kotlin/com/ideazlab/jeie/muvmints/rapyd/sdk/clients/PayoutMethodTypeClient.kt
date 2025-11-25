package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.ListPayoutMethodTypesResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.PayoutRequiredFieldsResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface PayoutMethodTypeClient {

    @Get("/v1/payout_methods")
    fun listPayoutMethodTypes(
        @QueryValue params: Map<String, String?>?,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): ListPayoutMethodTypesResponse

    @Get("/v1/payout_methods/{pomt}/required_fields")
    fun getPayoutRequiredFields(
        @PathVariable("pomt") payoutMethodType: String,
        @QueryValue("beneficiary_country") beneficiaryCountry: String,
        @QueryValue("beneficiary_entity_type") beneficiaryEntityType: String,
        @QueryValue("payout_amount") payoutAmount: String,
        @QueryValue("payout_currency") payoutCurrency: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): PayoutRequiredFieldsResponse
}
