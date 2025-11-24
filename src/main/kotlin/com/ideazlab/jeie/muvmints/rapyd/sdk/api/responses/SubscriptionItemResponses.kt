package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.ideazlab.jeie.muvmints.rapyd.sdk.Response
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import io.micronaut.serde.annotation.Serdeable

@Response
data class SubscriptionItemListResponse(
    val status: RapydStatus,
    val data: List<RapydSubscriptionItem>
)

@Response
data class SubscriptionItemResponse(
    val status: RapydStatus,
    val data: RapydSubscriptionItem
)

@Response
data class SubscriptionItemDeleteResponse(
    val status: RapydStatus,
    val data: RapydDeletedItem
)

@Response
data class UsageRecordSummariesResponse(
    val status: RapydStatus,
    val data: List<RapydUsageRecord>
)

@Response
data class UsageRecordResponse(
    val status: RapydStatus,
    val data: RapydUsageRecord
)

@Serdeable
data class RapydSubscriptionItem(
    val id: String,
    val created: Long?,
    val metadata: Map<String, Any?>?,
    val plan: Any?,
    val quantity: Int?,
    @param:JsonProperty("subscription_id")
    val subscriptionId: String?
)

@Serdeable
data class RapydUsageRecord(
    val id: String?,
    val quantity: Int?,
    @param:JsonProperty("subscription_item")
    val subscriptionItem: String?,
    val timestamp: Long?
)
