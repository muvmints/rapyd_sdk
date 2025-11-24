package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateSubscriptionByHostedPageRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateSubscriptionItemRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateSubscriptionItemUsageRecordRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateSubscriptionRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.UpdateSubscriptionItemRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.UpdateSubscriptionRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionHostedPageResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionItemDeleteResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionItemListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionItemResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.SubscriptionResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.UsageRecordResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.UsageRecordSummariesResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CustomerResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.DiscountResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface SubscriptionClient {

    @Get("/v1/payments/subscriptions")
    fun listSubscriptions(
        @QueryValue params: Map<String, String?>?,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionListResponse

    @Post("/v1/payments/subscriptions")
    fun createSubscription(
        @Body body: CreateSubscriptionRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionResponse

    @Get("/v1/payments/subscriptions/{subscriptionId}")
    fun retrieveSubscription(
        @PathVariable subscriptionId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionResponse

    @Post("/v1/payments/subscriptions/{subscriptionId}")
    fun updateSubscription(
        @PathVariable subscriptionId: String,
        @Body body: UpdateSubscriptionRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionResponse

    @Delete("/v1/payments/subscriptions/{subscriptionId}")
    fun cancelSubscription(
        @PathVariable subscriptionId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionResponse

    @Delete("/v1/payments/subscriptions/{subscriptionId}/discount")
    fun deleteSubscriptionDiscount(
        @PathVariable subscriptionId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): DiscountResponse

    @Post("/v1/checkout/subscriptions")
    fun createSubscriptionByHostedPage(
        @Body body: CreateSubscriptionByHostedPageRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionHostedPageResponse

    // ---- Subscription Items ----

    @Get("/v1/subscription_items")
    fun listSubscriptionItems(
        @QueryValue params: Map<String, String?>?,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionItemListResponse

    @Post("/v1/subscription_items")
    fun createSubscriptionItem(
        @Body body: CreateSubscriptionItemRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionItemResponse

    @Get("/v1/subscription_items/{subscriptionItemId}")
    fun retrieveSubscriptionItem(
        @PathVariable subscriptionItemId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionItemResponse

    @Post("/v1/subscription_items/{subscriptionItemId}")
    fun updateSubscriptionItem(
        @PathVariable subscriptionItemId: String,
        @Body body: UpdateSubscriptionItemRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionItemResponse

    @Delete("/v1/subscription_items/{subscriptionItemId}")
    fun deleteSubscriptionItem(
        @PathVariable subscriptionItemId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): SubscriptionItemDeleteResponse

    @Get("/v1/subscription_items/{subscriptionItemId}/usage_record_summaries")
    fun listSubscriptionItemUsageRecords(
        @PathVariable subscriptionItemId: String,
        @QueryValue params: Map<String, String?>?,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): UsageRecordSummariesResponse

    @Post("/v1/subscription_items/{subscriptionItemId}/usage_records")
    fun createSubscriptionItemUsageRecord(
        @PathVariable subscriptionItemId: String,
        @Body body: CreateSubscriptionItemUsageRecordRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): UsageRecordResponse
}
