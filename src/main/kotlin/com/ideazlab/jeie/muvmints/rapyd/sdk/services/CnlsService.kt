package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.InitiateMerchantQueryRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.InitiateMerchantQueryResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.RetrieveQueryResultsResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.CnlsClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(bean = RapydConfig::class)
class CnlsService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: CnlsClient
): BaseService() {

    fun initiateMerchantQuery(body: InitiateMerchantQueryRequest): InitiateMerchantQueryResponse {
        val path = "/v1/cnl/termination_query"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.initiateMerchantQuery(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun retrieveQueryResults(partnerQueryReference: String): RetrieveQueryResultsResponse {
        val path = "/v1/cnl/termination_query/$partnerQueryReference"
        val signed = sign("get", path, null, config)
        return client.retrieveQueryResults(
            partnerQueryReference = partnerQueryReference,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
}
