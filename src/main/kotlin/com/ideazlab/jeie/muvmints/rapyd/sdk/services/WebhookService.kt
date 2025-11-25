package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.WebhookListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.WebhookResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.WebhookClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(bean = RapydConfig::class)
class WebhookService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: WebhookClient
) : BaseService() {

    fun listWebhooks(params: Map<String, String?> = emptyMap()): WebhookListResponse {
        val filtered = params.filterValues { !it.isNullOrBlank() }
        val ordered = linkedMapOf<String, String>()
        filtered.keys.sorted().forEach { k -> ordered[k] = filtered[k]!! }
        val query = if (ordered.isNotEmpty()) ordered.entries.joinToString("&") { (k, v) -> "$k=$v" } else null
        val pathWithQuery = buildString {
            append("/v1/webhooks")
            if (!query.isNullOrBlank()) append("?").append(query)
        }
        val signed = sign("get", pathWithQuery, null, config)
        return client.listWebhooks(
            params = if (ordered.isEmpty()) null else ordered,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getWebhook(webhookId: String): WebhookResponse {
        val path = "/v1/webhooks/$webhookId"
        val signed = sign("get", path, null, config)
        return client.getWebhook(
            webhook = webhookId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun resendWebhook(webhookId: String): WebhookResponse {
        val path = "/v1/webhooks/$webhookId"
        val signed = sign("post", path, null, config)
        return client.resendWebhook(
            webhook = webhookId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
}
