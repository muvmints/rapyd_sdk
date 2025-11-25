package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.TestUtils
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.*
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.*
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydPayment
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.OrderClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class OrderServiceTest {
    private val config: RapydConfig = TestUtils.testConfig()
    private val objectMapper: ObjectMapper = TestUtils.objectMapper()
    private val client: OrderClient = mock()
    private val service = OrderService(config, objectMapper, client)

    @Test
    fun listOrders_filtersNulls_ordersKeys_andPassesHeaders() {
        val params = mapOf(
            "customer" to "cus_1",
            "limit" to "10",
            "cursor" to null,
            "status" to "open",
            "" to "",
            "from" to "2024-01-01"
        )
        val expectedOrdered = linkedMapOf(
            "customer" to "cus_1",
            "from" to "2024-01-01",
            "limit" to "10",
            "status" to "open"
        )

        val stub = OrderListResponse(RapydStatus(null, "OK", null, null, null), data = emptyList())
        whenever(client.listOrders(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listOrders(params)
        assertEquals(stub, out)

        verify(client).listOrders(
            params = argThat { this.toList() == expectedOrdered.toList() },
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listOrders_whenNoParams_passesNull_andHeaders() {
        val stub = OrderListResponse(RapydStatus(null, "OK", null, null, null), data = emptyList())
        whenever(client.listOrders(isNull(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listOrders(emptyMap())
        assertEquals(stub, out)

        verify(client).listOrders(
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun createOrder_passesBody_andHeaders() {
        val body = CreateOrderRequest(
            customer = "cus_1",
            currency = "USD",
            items = listOf(OrderItemRequest(parent = "sku_1", quantity = 2))
        )
        val stub = OrderResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydOrder(
                id = "ord_1",
                amount = 20.0,
                amountReturned = 0.0,
                payment = null,
                created = 1L,
                customer = body.customer,
                currency = body.currency,
                email = null,
                externalCouponCode = null,
                items = emptyList(),
                metadata = null,
                returns = null,
                shippingAddress = null,
                status = "new",
                statusTransitions = null,
                updated = 1L,
                upstreamId = null,
                taxPercent = null
            )
        )
        whenever(client.createOrder(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.createOrder(body)
        assertEquals(stub, out)

        verify(client).createOrder(
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun getOrder_passesPath_andHeaders() {
        val orderId = "ord_X"
        val stub = OrderResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydOrder(
                id = orderId,
                amount = 10.0,
                amountReturned = 0.0,
                payment = RapydPayment(
                    id = "pay_1",
                    amount = 10.0,
                    currency = "USD",
                    status = "closed",
                    description = null
                ),
                created = 1L,
                customer = "cus_1",
                currency = "USD",
                email = null,
                externalCouponCode = null,
                items = emptyList(),
                metadata = null,
                returns = null,
                shippingAddress = null,
                status = "closed",
                statusTransitions = null,
                updated = 1L,
                upstreamId = null,
                taxPercent = null
            )
        )
        whenever(client.getOrder(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.getOrder(orderId)
        assertEquals(stub, out)

        verify(client).getOrder(
            orderId = eq(orderId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun updateOrder_passesPathBody_andHeaders() {
        val orderId = "ord_U"
        val body = UpdateOrderRequest(status = "paid", taxPercent = 5.0)
        val stub = OrderResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydOrder(
                id = orderId,
                amount = 10.0,
                amountReturned = 0.0,
                payment = null,
                created = 1L,
                customer = "cus_1",
                currency = "USD",
                email = null,
                externalCouponCode = null,
                items = emptyList(),
                metadata = null,
                returns = null,
                shippingAddress = null,
                status = body.status,
                statusTransitions = null,
                updated = 2L,
                upstreamId = null,
                taxPercent = body.taxPercent
            )
        )
        whenever(client.updateOrder(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.updateOrder(orderId, body)
        assertEquals(stub, out)

        verify(client).updateOrder(
            orderId = eq(orderId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun createOrderReturn_passesPathBody_andHeaders() {
        val orderId = "ord_R"
        val body = CreateOrderReturnRequest(
            orderId = orderId,
            items = listOf(OrderReturnItemRequest(parent = "sku_1", quantity = 1))
        )
        val stub = OrderReturnResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydOrderReturn(
                id = "ret_1",
                amount = 10.0,
                created = 2L,
                currency = "USD",
                items = emptyList(),
                order = orderId,
                refund = "rf_1"
            )
        )
        whenever(client.createOrderReturn(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.createOrderReturn(orderId, body)
        assertEquals(stub, out)

        verify(client).createOrderReturn(
            orderId = eq(orderId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listOrderReturns_filtersNulls_ordersKeys_andPassesHeaders() {
        val params = mapOf(
            "order" to "ord_1",
            "limit" to "10",
            "cursor" to null,
            "from" to "2024-01-01"
        )
        val expectedOrdered = linkedMapOf(
            "from" to "2024-01-01",
            "limit" to "10",
            "order" to "ord_1"
        )
        val stub = OrderReturnListResponse(RapydStatus(null, "OK", null, null, null), data = emptyList())
        whenever(client.listOrderReturns(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listOrderReturns(params)
        assertEquals(stub, out)

        verify(client).listOrderReturns(
            params = argThat { this.toList() == expectedOrdered.toList() },
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listOrderReturns_whenNoParams_passesNull_andHeaders() {
        val stub = OrderReturnListResponse(RapydStatus(null, "OK", null, null, null), data = emptyList())
        whenever(client.listOrderReturns(isNull(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listOrderReturns(emptyMap())
        assertEquals(stub, out)

        verify(client).listOrderReturns(
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun payOrder_passesPathBody_andHeaders() {
        val orderId = "ord_P"
        val body = PayOrderRequest(paymentMethod = "pm_1", customer = "cus_1")
        val stub = OrderResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydOrder(
                id = orderId,
                amount = 10.0,
                amountReturned = 0.0,
                payment = RapydPayment(
                    id = "pay_1",
                    amount = 10.0,
                    currency = "USD",
                    status = "closed",
                    description = null
                ),
                created = 1L,
                customer = "cus_1",
                currency = "USD",
                email = null,
                externalCouponCode = null,
                items = emptyList(),
                metadata = null,
                returns = null,
                shippingAddress = null,
                status = "paid",
                statusTransitions = null,
                updated = 2L,
                upstreamId = null,
                taxPercent = null
            )
        )
        whenever(client.payOrder(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.payOrder(orderId, body)
        assertEquals(stub, out)

        verify(client).payOrder(
            orderId = eq(orderId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }
}
