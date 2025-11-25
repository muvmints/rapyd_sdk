package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.ideazlab.jeie.muvmints.rapyd.sdk.Response
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import io.micronaut.serde.annotation.Serdeable

@Response
data class WebhookListResponse(
    val status: RapydStatus,
    val data: List<RapydWebhook>
)

@Response
data class WebhookResponse(
    val status: RapydStatus,
    val data: RapydWebhook
)

@Serdeable
data class RapydWebhook(
    val attempts: List<RapydWebhookAttempt>?,
    @param:JsonProperty("created_at")
    val createdAt: Long?,
    // Rapyd webhook payload is dynamic per event type
    val data: Map<String, Any?>?,
    @param:JsonProperty("last_attempt_at")
    val lastAttemptAt: Long?,
    @param:JsonProperty("next_attempt_at")
    val nextAttemptAt: Long?,
    val status: String?,
    val token: String?,
    // Spec marks as number (internal). Keep it flexible.
    val type: Number?
)

@Serdeable
data class RapydWebhookAttempt(
    val error: String?,
    @param:JsonProperty("http_status_code")
    val httpStatusCode: String?,
    @param:JsonProperty("http_response_body")
    val httpResponseBody: String?,
    @param:JsonProperty("http_response_headers")
    val httpResponseHeaders: RapydWebhookAttemptHeaders?
)

@Serdeable
data class RapydWebhookAttemptHeaders(
    val connection: String?,
    @param:JsonProperty("content-length")
    val contentLength: Number?,
    @param:JsonProperty("content-type")
    val contentType: String?,
    val date: String?,
    val server: String?
)
