package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateOrderRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateOrderReturnRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.PayOrderRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.UpdateOrderRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.OrderListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.OrderResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.OrderReturnListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.OrderReturnResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface OrderClient {

    @Get("/v1/orders")
    fun listOrders(
        @QueryValue params: Map<String, String?>?,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): OrderListResponse

    @Post("/v1/orders")
    fun createOrder(
        @Body body: CreateOrderRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): OrderResponse

    @Get("/v1/orders/{orderId}")
    fun getOrder(
        @PathVariable("orderId") orderId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): OrderResponse

    @Post("/v1/orders/{orderId}")
    fun updateOrder(
        @PathVariable("orderId") orderId: String,
        @Body body: UpdateOrderRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): OrderResponse

    @Post("/v1/orders/{orderId}/returns")
    fun createOrderReturn(
        @PathVariable("orderId") orderId: String,
        @Body body: CreateOrderReturnRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): OrderReturnResponse

    @Get("/v1/order_returns")
    fun listOrderReturns(
        @QueryValue params: Map<String, String?>?,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): OrderReturnListResponse

    @Post("/v1/orders/{orderId}/pay")
    fun payOrder(
        @PathVariable("orderId") orderId: String,
        @Body body: PayOrderRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): OrderResponse
}
