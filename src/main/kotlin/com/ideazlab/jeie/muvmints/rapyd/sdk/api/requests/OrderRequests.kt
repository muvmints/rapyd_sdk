package com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CreateOrderRequest(
    val customer: String,
    val currency: String,
    val items: List<OrderItemRequest>,
    val coupon: String? = null,
    val email: String? = null,
    val metadata: Map<String, Any?>? = null,
    @param:JsonProperty("shipping_address")
    val shippingAddress: RapydAddressRequest? = null,
    @param:JsonProperty("tax_percent")
    val taxPercent: Double? = null,
    @param:JsonProperty("upstream_id")
    val upstreamId: String? = null
)

@Serdeable
data class OrderItemRequest(
    val amount: String? = null,
    val currency: String? = null,
    val description: String? = null,
    val parent: String? = null,
    val type: String? = null, // shipping | sku
    val quantity: Number? = null
)

@Serdeable
data class UpdateOrderRequest(
    val coupon: String? = null,
    val metadata: Map<String, Any?>? = null,
    @param:JsonProperty("tax_percent")
    val taxPercent: Double? = null,
    val status: String? = null // paid | pending | canceled | fulfilled | returned | partial
)

@Serdeable
data class CreateOrderReturnRequest(
    @param:JsonProperty("order_id")
    val orderId: String? = null,
    val items: List<OrderReturnItemRequest>
)

@Serdeable
data class OrderReturnItemRequest(
    val description: String? = null,
    val parent: String? = null,
    val type: String? = null, // sku | shipping | tax
    val quantity: Number? = null,
    val currency: String? = null,
    val amount: Number? = null,
    @param:JsonProperty("order_id")
    val orderId: String? = null
)

@Serdeable
data class PayOrderRequest(
    val metadata: Map<String, Any?>? = null,
    @param:JsonProperty("payment_method")
    val paymentMethod: String? = null,
    val customer: String? = null
)
