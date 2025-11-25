package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateHostedVerifyApplicationRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.VerifyIdentityRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.*
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.VerifyClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(bean = RapydConfig::class)
class VerifyService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: VerifyClient
): BaseService() {

    fun verifyIdentity(body: VerifyIdentityRequest): VerifyIdentityResponse {
        val path = "/v1/identities"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.verifyIdentity(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun listIdDocuments(country: String?): VerifyIdDocumentsResponse {
        val query = buildString {
            val params = mutableListOf<String>()
            if (!country.isNullOrBlank()) params.add("country=$country")
            if (params.isNotEmpty()) append("?" + params.joinToString("&"))
        }
        val path = "/v1/identities/types$query"
        val signed = sign("get", path, null, config)
        return client.listIdDocuments(
            country = country,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getApplicationTypesByCountry(country: String): VerifyApplicationTypesByCountryResponse {
        val path = "/v1/verify/applications/types/country/$country"
        val signed = sign("get", path, null, config)
        return client.getApplicationTypesByCountry(
            country = country,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getApplicationStatus(applicationId: String): VerifyApplicationStatusResponse {
        val path = "/v1/verify/applications/status/$applicationId"
        val signed = sign("get", path, null, config)
        return client.getApplicationStatus(
            applicationId = applicationId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun createHostedApplication(body: CreateHostedVerifyApplicationRequest): VerifyHostedApplicationResponse {
        val path = "/v1/verify/applications/hosted"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createHostedApplication(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getHostedApplication(verifyAppId: String): VerifyHostedApplicationResponse {
        val path = "/v1/verify/applications/hosted/$verifyAppId"
        val signed = sign("get", path, null, config)
        return client.getHostedApplication(
            verifyAppId = verifyAppId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
}
