package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.ideazlab.jeie.muvmints.rapyd.sdk.Response
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydPayment
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import io.micronaut.serde.annotation.Serdeable

@Response
data class OrderListResponse(
    val status: RapydStatus,
    val data: List<RapydOrder>
)

@Response
data class OrderResponse(
    val status: RapydStatus,
    val data: RapydOrder
)

@Response
data class OrderReturnResponse(
    val status: RapydStatus,
    val data: RapydOrderReturn
)

@Response
data class OrderReturnListResponse(
    val status: RapydStatus,
    val data: List<RapydOrderReturn>
)

@Serdeable
data class RapydOrder(
    val id: String?,
    val amount: Double?,
    @param:JsonProperty("amount_returned")
    val amountReturned: Double?,
    val payment: RapydPayment?,
    val created: Long?,
    val customer: String?,
    val currency: String?,
    val email: String?,
    @param:JsonProperty("external_coupon_code")
    val externalCouponCode: String?,
    val items: List<RapydOrderItem>?,
    val metadata: Map<String, Any?>?,
    val returns: List<RapydOrderReturnedItem>?,
    @param:JsonProperty("shipping_address")
    val shippingAddress: Map<String, Any?>?,
    val status: String?,
    @param:JsonProperty("status_transitions")
    val statusTransitions: RapydOrderStatusTransitions?,
    val updated: Long?,
    @param:JsonProperty("upstream_id")
    val upstreamId: String?,
    @param:JsonProperty("tax_percent")
    val taxPercent: Double?
)

@Serdeable
data class RapydOrderItem(
    val amount: Double?,
    val currency: String?,
    val description: String?,
    val parent: String?,
    val quantity: Number?,
    val type: String?
)

@Serdeable
data class RapydOrderReturn(
    val id: String?,
    val amount: Double?,
    val created: Long?,
    val currency: String?,
    val items: List<RapydOrderReturnedItem>?,
    val order: String?,
    val refund: String?
)

@Serdeable
data class RapydOrderReturnedItem(
    val amount: Double?,
    val currency: String?,
    val description: String?,
    val parent: String?,
    val quantity: Number?,
    val type: String?
)

@Serdeable
data class RapydOrderStatusTransitions(
    val canceled: Long?,
    val fulfilled: Long?,
    val paid: Long?,
    val returned: Long?,
    val pending: Long?,
    val partial: Long?
)
