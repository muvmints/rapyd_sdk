package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.WebhookListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.WebhookResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface WebhookClient {

    @Get("/v1/webhooks")
    fun listWebhooks(
        @QueryValue params: Map<String, String?>?,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): WebhookListResponse

    @Get("/v1/webhooks/{webhook}")
    fun getWebhook(
        @PathVariable("webhook") webhook: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): WebhookResponse

    @Post("/v1/webhooks/{webhook}")
    fun resendWebhook(
        @PathVariable("webhook") webhook: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): WebhookResponse
}
