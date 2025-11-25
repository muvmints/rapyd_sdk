package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.ideazlab.jeie.muvmints.rapyd.sdk.TestUtils
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.*
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.*
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.SubscriptionClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class SubscriptionServiceTest {
    private val config = TestUtils.testConfig()
    private val objectMapper = TestUtils.objectMapper()
    private val client: SubscriptionClient = mock()
    private val service = SubscriptionService(config, objectMapper, client)

    // ---- Subscriptions ----

    @Test
    fun listSubscriptions_filtersAndSortsParams() {
        val input = mapOf("z" to "9", "a" to "1", "empty" to "", "blank" to "   ", "nullish" to null)
        val expected = linkedMapOf("a" to "1", "z" to "9")
        val stub = SubscriptionListResponse(RapydStatus(null, "OK", null, null, null), emptyList())
        whenever(client.listSubscriptions(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.listSubscriptions(input)
        assertEquals(stub, res)

        verify(client).listSubscriptions(
            params = eq(expected as Map<String, String?>),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listSubscriptions_emptyParams_passesNull() {
        val stub = SubscriptionListResponse(RapydStatus(null, "OK", null, null, null), emptyList())
        whenever(client.listSubscriptions(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        service.listSubscriptions()

        verify(client).listSubscriptions(
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun createSubscription_passesBodyAndHeaders() {
        val body = CreateSubscriptionRequest(customer = "c_1", currency = "USD", description = "sub desc")
        val stub = SubscriptionResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydSubscription(
                id = "sub_1",
                status = "active",
                customer = body.customer,
                currency = body.currency,
                description = body.description,
                items = emptyList<Any>(),
                currentPeriodStart = null,
                currentPeriodEnd = null,
                cancelAtPeriodEnd = null,
                createdAt = null
            )
        )
        whenever(client.createSubscription(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.createSubscription(body)
        assertEquals(stub, res)

        verify(client).createSubscription(
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun retrieveSubscription_passesHeaders() {
        val subscriptionId = "sub_123"
        val stub = SubscriptionResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydSubscription(
                id = subscriptionId,
                status = null,
                customer = null,
                currency = null,
                description = null,
                items = null,
                currentPeriodStart = null,
                currentPeriodEnd = null,
                cancelAtPeriodEnd = null,
                createdAt = null
            )
        )
        whenever(client.retrieveSubscription(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.retrieveSubscription(subscriptionId)
        assertEquals(stub, res)

        verify(client).retrieveSubscription(
            subscriptionId = eq(subscriptionId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun updateSubscription_passesBodyAndHeaders() {
        val subscriptionId = "sub_456"
        val body = UpdateSubscriptionRequest(description = "updated")
        val stub = SubscriptionResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydSubscription(
                id = subscriptionId,
                status = "active",
                customer = null,
                currency = null,
                description = body.description,
                items = null,
                currentPeriodStart = null,
                currentPeriodEnd = null,
                cancelAtPeriodEnd = null,
                createdAt = null
            )
        )
        whenever(client.updateSubscription(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.updateSubscription(subscriptionId, body)
        assertEquals(stub, res)

        verify(client).updateSubscription(
            subscriptionId = eq(subscriptionId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun cancelSubscription_passesHeaders() {
        val subscriptionId = "sub_789"
        val stub = SubscriptionResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydSubscription(
                id = subscriptionId,
                status = "canceled",
                customer = null,
                currency = null,
                description = null,
                items = null,
                currentPeriodStart = null,
                currentPeriodEnd = null,
                cancelAtPeriodEnd = null,
                createdAt = null
            )
        )
        whenever(client.cancelSubscription(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.cancelSubscription(subscriptionId)
        assertEquals(stub, res)

        verify(client).cancelSubscription(
            subscriptionId = eq(subscriptionId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun deleteSubscriptionDiscount_passesHeaders() {
        val subscriptionId = "sub_disc_1"
        val stub = DiscountResponse(
            RapydStatus(null, "OK", null, null, null),
            data = com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydDiscount(
                id = null,
                amountOff = null,
                percentOff = null,
                currency = null,
                description = null,
                duration = null,
                durationInMonths = null,
                discountDurationInUses = null,
                discountValidUntil = null,
                discountValidityInMonths = null,
                valid = null,
                created = null,
                metadata = null,
                maxRedemptions = null,
                redeemBy = null,
                timesRedeemed = null
            )
        )
        whenever(client.deleteSubscriptionDiscount(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.deleteSubscriptionDiscount(subscriptionId)
        assertEquals(stub, res)

        verify(client).deleteSubscriptionDiscount(
            subscriptionId = eq(subscriptionId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun createSubscriptionByHostedPage_passesBodyAndHeaders() {
        val body = CreateSubscriptionByHostedPageRequest(
            customer = "c_hp",
            currency = "USD",
            completeCheckoutUrl = "https://ok",
            cancelCheckoutUrl = "https://cancel"
        )
        val stub = SubscriptionHostedPageResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydSubscriptionHostedPage(
                id = "hp_1",
                status = "created",
                redirectUrl = null,
                completeCheckoutUrl = body.completeCheckoutUrl,
                cancelCheckoutUrl = body.cancelCheckoutUrl,
                errorPaymentUrl = null,
                pageExpiration = null
            )
        )
        whenever(client.createSubscriptionByHostedPage(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.createSubscriptionByHostedPage(body)
        assertEquals(stub, res)

        verify(client).createSubscriptionByHostedPage(
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    // ---- Subscription Items ----

    @Test
    fun listSubscriptionItems_filtersAndSortsParams() {
        val input = mapOf("z" to "9", "a" to "1", "empty" to "", "nullish" to null)
        val expected = linkedMapOf("a" to "1", "z" to "9")
        val stub = SubscriptionItemListResponse(RapydStatus(null, "OK", null, null, null), emptyList())
        whenever(client.listSubscriptionItems(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.listSubscriptionItems(input)
        assertEquals(stub, res)

        verify(client).listSubscriptionItems(
            params = eq(expected as Map<String, String?>),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listSubscriptionItems_emptyParams_passesNull() {
        val stub = SubscriptionItemListResponse(RapydStatus(null, "OK", null, null, null), emptyList())
        whenever(client.listSubscriptionItems(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        service.listSubscriptionItems(emptyMap())

        verify(client).listSubscriptionItems(
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun createSubscriptionItem_passesBodyAndHeaders() {
        val body = CreateSubscriptionItemRequest(plan = "pl_1", subscription = "sub_1", quantity = 2)
        val stub = SubscriptionItemResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydSubscriptionItem(
                id = "si_1",
                created = null,
                metadata = null,
                plan = null,
                quantity = body.quantity,
                subscriptionId = body.subscription
            )
        )
        whenever(client.createSubscriptionItem(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.createSubscriptionItem(body)
        assertEquals(stub, res)

        verify(client).createSubscriptionItem(
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun retrieveSubscriptionItem_passesHeaders() {
        val subscriptionItemId = "si_123"
        val stub = SubscriptionItemResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydSubscriptionItem(
                id = subscriptionItemId,
                created = null,
                metadata = null,
                plan = null,
                quantity = null,
                subscriptionId = null
            )
        )
        whenever(client.retrieveSubscriptionItem(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.retrieveSubscriptionItem(subscriptionItemId)
        assertEquals(stub, res)

        verify(client).retrieveSubscriptionItem(
            subscriptionItemId = eq(subscriptionItemId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun updateSubscriptionItem_passesBodyAndHeaders() {
        val subscriptionItemId = "si_456"
        val body = UpdateSubscriptionItemRequest(quantity = 5)
        val stub = SubscriptionItemResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydSubscriptionItem(
                id = subscriptionItemId,
                created = null,
                metadata = null,
                plan = null,
                quantity = body.quantity,
                subscriptionId = null
            )
        )
        whenever(client.updateSubscriptionItem(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.updateSubscriptionItem(subscriptionItemId, body)
        assertEquals(stub, res)

        verify(client).updateSubscriptionItem(
            subscriptionItemId = eq(subscriptionItemId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun deleteSubscriptionItem_passesHeaders() {
        val subscriptionItemId = "si_789"
        val stub = SubscriptionItemDeleteResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydDeletedItem(deleted = true, id = subscriptionItemId)
        )
        whenever(client.deleteSubscriptionItem(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.deleteSubscriptionItem(subscriptionItemId)
        assertEquals(stub, res)

        verify(client).deleteSubscriptionItem(
            subscriptionItemId = eq(subscriptionItemId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listSubscriptionItemUsageRecords_filtersAndSortsParams() {
        val subscriptionItemId = "si_usage_1"
        val input = mapOf("b" to "2", "a" to "1", "empty" to "", "nullish" to null)
        val expected = linkedMapOf("a" to "1", "b" to "2")
        val stub = UsageRecordSummariesResponse(RapydStatus(null, "OK", null, null, null), emptyList())
        whenever(client.listSubscriptionItemUsageRecords(any(), any(), any(), any(), any(), any(), any())).thenReturn(
            stub
        )

        val res = service.listSubscriptionItemUsageRecords(subscriptionItemId, input)
        assertEquals(stub, res)

        verify(client).listSubscriptionItemUsageRecords(
            subscriptionItemId = eq(subscriptionItemId),
            params = eq(expected as Map<String, String?>),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listSubscriptionItemUsageRecords_emptyParams_passesNull() {
        val subscriptionItemId = "si_usage_2"
        val stub = UsageRecordSummariesResponse(RapydStatus(null, "OK", null, null, null), emptyList())
        whenever(client.listSubscriptionItemUsageRecords(any(), any(), any(), any(), any(), any(), any())).thenReturn(
            stub
        )

        service.listSubscriptionItemUsageRecords(subscriptionItemId)

        verify(client).listSubscriptionItemUsageRecords(
            subscriptionItemId = eq(subscriptionItemId),
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun createSubscriptionItemUsageRecord_passesBodyAndHeaders() {
        val subscriptionItemId = "si_usage_create"
        val body = CreateSubscriptionItemUsageRecordRequest(quantity = 3, timestamp = 1700000000)
        val stub = UsageRecordResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydUsageRecord(
                id = "ur_1",
                quantity = body.quantity,
                subscriptionItem = subscriptionItemId,
                timestamp = body.timestamp
            )
        )
        whenever(client.createSubscriptionItemUsageRecord(any(), any(), any(), any(), any(), any(), any())).thenReturn(
            stub
        )

        val res = service.createSubscriptionItemUsageRecord(subscriptionItemId, body)
        assertEquals(stub, res)

        verify(client).createSubscriptionItemUsageRecord(
            subscriptionItemId = eq(subscriptionItemId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    // ---- Invoice Items ----

    @Test
    fun listInvoiceItems_filtersAndSortsParams() {
        val input = mapOf("b" to "2", "a" to "1", "empty" to "", "nullish" to null)
        val expected = linkedMapOf("a" to "1", "b" to "2")
        val stub = InvoiceItemListResponse(RapydStatus(null, "OK", null, null, null), emptyList())
        whenever(client.listInvoiceItems(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.listInvoiceItems(input)
        assertEquals(stub, res)

        verify(client).listInvoiceItems(
            params = eq(expected as Map<String, String?>),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listInvoiceItems_emptyParams_passesNull() {
        val stub = InvoiceItemListResponse(RapydStatus(null, "OK", null, null, null), emptyList())
        whenever(client.listInvoiceItems(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        service.listInvoiceItems()

        verify(client).listInvoiceItems(
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun createInvoiceItem_passesBodyAndHeaders() {
        val body = CreateInvoiceItemRequest(
            amount = 9.99,
            currency = "USD",
            customer = "c_inv",
            description = "desc",
            quantity = 1
        )
        val stub = InvoiceItemListResponse(
            RapydStatus(null, "OK", null, null, null),
            data = listOf(
                RapydInvoiceItem(
                    id = "ii_1",
                    amount = body.amount,
                    currency = body.currency,
                    customer = body.customer,
                    date = null,
                    description = body.description,
                    discountable = null,
                    invoiceItem = null,
                    invoice = null,
                    metadata = body.metadata,
                    period = null,
                    plan = null,
                    proration = null,
                    quantity = body.quantity,
                    subscription = body.subscription,
                    unitAmount = body.unit_amount
                )
            )
        )
        whenever(client.createInvoiceItem(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.createInvoiceItem(body)
        assertEquals(stub, res)

        verify(client).createInvoiceItem(
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun retrieveInvoiceItem_passesHeaders() {
        val invoiceItemId = "ii_get_1"
        val stub = InvoiceItemListResponse(
            RapydStatus(null, "OK", null, null, null),
            data = listOf(
                RapydInvoiceItem(
                    id = invoiceItemId,
                    amount = null,
                    currency = null,
                    customer = null,
                    date = null,
                    description = null,
                    discountable = null,
                    invoiceItem = null,
                    invoice = null,
                    metadata = null,
                    period = null,
                    plan = null,
                    proration = null,
                    quantity = null,
                    subscription = null,
                    unitAmount = null
                )
            )
        )
        whenever(client.retrieveInvoiceItem(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.retrieveInvoiceItem(invoiceItemId)
        assertEquals(stub, res)

        verify(client).retrieveInvoiceItem(
            invoiceItem = eq(invoiceItemId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun updateInvoiceItem_passesBodyAndHeaders() {
        val invoiceItemId = "ii_upd_1"
        val body = UpdateInvoiceItemRequest(description = "upd")
        val stub = InvoiceItemListResponse(
            RapydStatus(null, "OK", null, null, null),
            data = listOf(
                RapydInvoiceItem(
                    id = invoiceItemId,
                    amount = null,
                    currency = null,
                    customer = null,
                    date = null,
                    description = body.description,
                    discountable = null,
                    invoiceItem = null,
                    invoice = null,
                    metadata = null,
                    period = null,
                    plan = null,
                    proration = null,
                    quantity = null,
                    subscription = null,
                    unitAmount = null
                )
            )
        )
        whenever(client.updateInvoiceItem(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.updateInvoiceItem(invoiceItemId, body)
        assertEquals(stub, res)

        verify(client).updateInvoiceItem(
            invoiceItem = eq(invoiceItemId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun deleteInvoiceItem_passesHeaders() {
        val invoiceItemId = "ii_del_1"
        val stub = InvoiceItemDeleteResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydDeletedItem(deleted = true, id = invoiceItemId)
        )
        whenever(client.deleteInvoiceItem(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.deleteInvoiceItem(invoiceItemId)
        assertEquals(stub, res)

        verify(client).deleteInvoiceItem(
            invoiceItem = eq(invoiceItemId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }
}
