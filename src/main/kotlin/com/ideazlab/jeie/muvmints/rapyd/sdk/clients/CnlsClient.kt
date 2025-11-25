package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.InitiateMerchantQueryRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.InitiateMerchantQueryResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.RetrieveQueryResultsResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface CnlsClient {

    @Post("/v1/cnl/termination_query")
    fun initiateMerchantQuery(
        @Body body: InitiateMerchantQueryRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): InitiateMerchantQueryResponse

    @Get("/v1/cnl/termination_query/{partner_query_reference}")
    fun retrieveQueryResults(
        @PathVariable("partner_query_reference") partnerQueryReference: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): RetrieveQueryResultsResponse
}
