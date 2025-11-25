package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateHostedVerifyApplicationRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.VerifyIdentityRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.*
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface VerifyClient {

    // POST /v1/identities - Verify Identity
    @Post("/v1/identities")
    fun verifyIdentity(
        @Body body: VerifyIdentityRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): VerifyIdentityResponse

    // GET /v1/identities/types - List ID Documents
    @Get("/v1/identities/types")
    fun listIdDocuments(
        @QueryValue("country") country: String?,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): VerifyIdDocumentsResponse

    // GET /v1/verify/applications/types/country/{country}
    @Get("/v1/verify/applications/types/country/{country}")
    fun getApplicationTypesByCountry(
        @PathVariable("country") country: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): VerifyApplicationTypesByCountryResponse

    // GET /v1/verify/applications/status/{application}
    @Get("/v1/verify/applications/status/{application}")
    fun getApplicationStatus(
        @PathVariable("application") applicationId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): VerifyApplicationStatusResponse

    // POST /v1/verify/applications/hosted
    @Post("/v1/verify/applications/hosted")
    fun createHostedApplication(
        @Body body: CreateHostedVerifyApplicationRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): VerifyHostedApplicationResponse

    // GET /v1/verify/applications/hosted/{verifyAppId}
    @Get("/v1/verify/applications/hosted/{verifyAppId}")
    fun getHostedApplication(
        @PathVariable("verifyAppId") verifyAppId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): VerifyHostedApplicationResponse
}
