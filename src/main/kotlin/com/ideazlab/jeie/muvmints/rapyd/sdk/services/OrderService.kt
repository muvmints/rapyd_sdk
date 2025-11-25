package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateOrderRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateOrderReturnRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.PayOrderRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.UpdateOrderRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.OrderListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.OrderResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.OrderReturnListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.OrderReturnResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.OrderClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(bean = RapydConfig::class)
class OrderService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: OrderClient
) : BaseService() {

    fun listOrders(params: Map<String, String?> = emptyMap()): OrderListResponse {
        val filtered = params.filterValues { !it.isNullOrBlank() }
        val ordered = linkedMapOf<String, String>()
        filtered.keys.sorted().forEach { k -> ordered[k] = filtered[k]!! }
        val query = if (ordered.isNotEmpty()) ordered.entries.joinToString("&") { (k, v) -> "$k=$v" } else null
        val pathWithQuery = buildString {
            append("/v1/orders")
            if (!query.isNullOrBlank()) append("?").append(query)
        }
        val signed = sign("get", pathWithQuery, null, config)
        return client.listOrders(
            params = if (ordered.isEmpty()) null else ordered,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun createOrder(body: CreateOrderRequest): OrderResponse {
        val path = "/v1/orders"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createOrder(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getOrder(orderId: String): OrderResponse {
        val path = "/v1/orders/$orderId"
        val signed = sign("get", path, null, config)
        return client.getOrder(
            orderId = orderId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun updateOrder(orderId: String, body: UpdateOrderRequest): OrderResponse {
        val path = "/v1/orders/$orderId"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.updateOrder(
            orderId = orderId,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun createOrderReturn(orderId: String, body: CreateOrderReturnRequest): OrderReturnResponse {
        val path = "/v1/orders/$orderId/returns"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createOrderReturn(
            orderId = orderId,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun listOrderReturns(params: Map<String, String?> = emptyMap()): OrderReturnListResponse {
        val filtered = params.filterValues { !it.isNullOrBlank() }
        val ordered = linkedMapOf<String, String>()
        filtered.keys.sorted().forEach { k -> ordered[k] = filtered[k]!! }
        val query = if (ordered.isNotEmpty()) ordered.entries.joinToString("&") { (k, v) -> "$k=$v" } else null
        val pathWithQuery = buildString {
            append("/v1/order_returns")
            if (!query.isNullOrBlank()) append("?").append(query)
        }
        val signed = sign("get", pathWithQuery, null, config)
        return client.listOrderReturns(
            params = if (ordered.isEmpty()) null else ordered,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun payOrder(orderId: String, body: PayOrderRequest): OrderResponse {
        val path = "/v1/orders/$orderId/pay"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.payOrder(
            orderId = orderId,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
}
