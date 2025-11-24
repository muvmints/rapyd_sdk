package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateSubscriptionByHostedPageRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateSubscriptionItemRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateSubscriptionItemUsageRecordRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateSubscriptionRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.UpdateSubscriptionItemRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.UpdateSubscriptionRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.DiscountResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionHostedPageResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionItemDeleteResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionItemListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionItemResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.UsageRecordResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.UsageRecordSummariesResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.SubscriptionClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(bean = RapydConfig::class)
class SubscriptionService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: SubscriptionClient
): BaseService() {

    fun listSubscriptions(params: Map<String, String?> = emptyMap()): SubscriptionListResponse {
        val filtered = params.filterValues { !it.isNullOrBlank() }
        val ordered = linkedMapOf<String, String>()
        filtered.keys.sorted().forEach { k -> ordered[k] = filtered[k]!! }
        val query = if (ordered.isNotEmpty()) ordered.entries.joinToString("&") { (k, v) -> "$k=$v" } else null
        val pathWithQuery = buildString {
            append("/v1/payments/subscriptions")
            if (!query.isNullOrBlank()) append("?").append(query)
        }
        val signed = sign("get", pathWithQuery, null, config)
        return client.listSubscriptions(
            params = if (ordered.isEmpty()) null else ordered,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun createSubscription(body: CreateSubscriptionRequest): SubscriptionResponse {
        val path = "/v1/payments/subscriptions"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createSubscription(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun retrieveSubscription(subscriptionId: String): SubscriptionResponse {
        val path = "/v1/payments/subscriptions/$subscriptionId"
        val signed = sign("get", path, null, config)
        return client.retrieveSubscription(
            subscriptionId = subscriptionId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun updateSubscription(subscriptionId: String, body: UpdateSubscriptionRequest): SubscriptionResponse {
        val path = "/v1/payments/subscriptions/$subscriptionId"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.updateSubscription(
            subscriptionId = subscriptionId,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun cancelSubscription(subscriptionId: String): SubscriptionResponse {
        val path = "/v1/payments/subscriptions/$subscriptionId"
        val signed = sign("delete", path, null, config)
        return client.cancelSubscription(
            subscriptionId = subscriptionId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun deleteSubscriptionDiscount(subscriptionId: String): DiscountResponse {
        val path = "/v1/payments/subscriptions/$subscriptionId/discount"
        val signed = sign("delete", path, null, config)
        return client.deleteSubscriptionDiscount(
            subscriptionId = subscriptionId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun createSubscriptionByHostedPage(body: CreateSubscriptionByHostedPageRequest): SubscriptionHostedPageResponse {
        val path = "/v1/checkout/subscriptions"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createSubscriptionByHostedPage(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    // ---- Subscription Items ----

    fun listSubscriptionItems(params: Map<String, String?>): SubscriptionItemListResponse {
        val filtered = params.filterValues { !it.isNullOrBlank() }
        val ordered = linkedMapOf<String, String>()
        filtered.keys.sorted().forEach { k -> ordered[k] = filtered[k]!! }
        val query = if (ordered.isNotEmpty()) ordered.entries.joinToString("&") { (k, v) -> "$k=$v" } else null
        val pathWithQuery = buildString {
            append("/v1/subscription_items")
            if (!query.isNullOrBlank()) append("?").append(query)
        }
        val signed = sign("get", pathWithQuery, null, config)
        return client.listSubscriptionItems(
            params = if (ordered.isEmpty()) null else ordered,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun createSubscriptionItem(body: CreateSubscriptionItemRequest): SubscriptionItemResponse {
        val path = "/v1/subscription_items"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createSubscriptionItem(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun retrieveSubscriptionItem(subscriptionItemId: String): SubscriptionItemResponse {
        val path = "/v1/subscription_items/$subscriptionItemId"
        val signed = sign("get", path, null, config)
        return client.retrieveSubscriptionItem(
            subscriptionItemId = subscriptionItemId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun updateSubscriptionItem(subscriptionItemId: String, body: UpdateSubscriptionItemRequest): SubscriptionItemResponse {
        val path = "/v1/subscription_items/$subscriptionItemId"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.updateSubscriptionItem(
            subscriptionItemId = subscriptionItemId,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun deleteSubscriptionItem(subscriptionItemId: String): SubscriptionItemDeleteResponse {
        val path = "/v1/subscription_items/$subscriptionItemId"
        val signed = sign("delete", path, null, config)
        return client.deleteSubscriptionItem(
            subscriptionItemId = subscriptionItemId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun listSubscriptionItemUsageRecords(subscriptionItemId: String, params: Map<String, String?> = emptyMap()): UsageRecordSummariesResponse {
        val filtered = params.filterValues { !it.isNullOrBlank() }
        val ordered = linkedMapOf<String, String>()
        filtered.keys.sorted().forEach { k -> ordered[k] = filtered[k]!! }
        val query = if (ordered.isNotEmpty()) ordered.entries.joinToString("&") { (k, v) -> "$k=$v" } else null
        val pathWithQuery = buildString {
            append("/v1/subscription_items/$subscriptionItemId/usage_record_summaries")
            if (!query.isNullOrBlank()) append("?").append(query)
        }
        val signed = sign("get", pathWithQuery, null, config)
        return client.listSubscriptionItemUsageRecords(
            subscriptionItemId = subscriptionItemId,
            params = if (ordered.isEmpty()) null else ordered,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun createSubscriptionItemUsageRecord(subscriptionItemId: String, body: CreateSubscriptionItemUsageRecordRequest): UsageRecordResponse {
        val path = "/v1/subscription_items/$subscriptionItemId/usage_records"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createSubscriptionItemUsageRecord(
            subscriptionItemId = subscriptionItemId,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
}
