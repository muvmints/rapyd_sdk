package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.TestUtils
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateInvoiceRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.PayInvoiceRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.UpdateInvoiceRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.*
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.InvoiceClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class InvoiceServiceTest {
    private val config: RapydConfig = TestUtils.testConfig()
    private val objectMapper: ObjectMapper = TestUtils.objectMapper()
    private val client: InvoiceClient = mock()
    private val service = InvoiceService(config, objectMapper, client)

    @Test
    fun listInvoices_filtersNulls_ordersKeys_andPassesHeaders() {
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
        val stub = InvoicesListResponse(
            RapydStatus(null, "OK", null, null, null),
            data = emptyList()
        )
        whenever(client.listInvoices(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listInvoices(params)
        assertEquals(stub, out)

        verify(client).listInvoices(
            params = argThat { this.toList() == expectedOrdered.toList() },
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listInvoices_whenNoParams_passesNull_andHeaders() {
        val stub = InvoicesListResponse(RapydStatus(null, "OK", null, null, null), data = emptyList())
        whenever(client.listInvoices(isNull(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listInvoices(emptyMap())
        assertEquals(stub, out)

        verify(client).listInvoices(
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun createInvoice_passesBody_andSignedHeaders() {
        val body = CreateInvoiceRequest(
            billing = "charge_automatically",
            currency = "USD",
            customer = "cus_1",
            description = "inv",
            metadata = mapOf("k" to "v"),
            payment_method = "pm_1"
        )
        val stub = InvoiceResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydInvoice(
                id = "inv_1",
                status = "draft",
                currency = body.currency,
                customer = body.customer,
                description = body.description,
                attemptCount = 0,
                automaticAttemptCount = 0,
                billing = body.billing,
                billingReason = null,
                createdAt = 1L,
                daysUntilDue = null,
                dueDate = null,
                hostedInvoiceUrl = null,
                invoicePdf = null,
                nextPaymentAttempt = null,
                number = null,
                paid = false,
                subtotal = 0.0,
                tax = 0.0,
                taxPercent = null,
                total = 0.0,
                lines = null,
                discount = null,
                metadata = body.metadata
            )
        )
        whenever(client.createInvoice(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.createInvoice(body)
        assertEquals(stub, out)

        verify(client).createInvoice(
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun retrieveInvoice_passesPath_andHeaders() {
        val invoiceId = "inv_X"
        val stub = InvoiceResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydInvoice(
                id = invoiceId,
                status = "open",
                currency = "USD",
                customer = "cus_1",
                description = null,
                attemptCount = 0,
                automaticAttemptCount = 0,
                billing = null,
                billingReason = null,
                createdAt = 1L,
                daysUntilDue = null,
                dueDate = null,
                hostedInvoiceUrl = null,
                invoicePdf = null,
                nextPaymentAttempt = null,
                number = null,
                paid = false,
                subtotal = 0.0,
                tax = 0.0,
                taxPercent = null,
                total = 0.0,
                lines = null,
                discount = null,
                metadata = null
            )
        )
        whenever(client.retrieveInvoice(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.retrieveInvoice(invoiceId)
        assertEquals(stub, out)

        verify(client).retrieveInvoice(
            invoiceId = eq(invoiceId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun updateInvoice_passesPathBody_andHeaders() {
        val invoiceId = "inv_U"
        val body = UpdateInvoiceRequest(description = "new desc", metadata = mapOf("a" to 1))
        val stub = InvoiceResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydInvoice(
                id = invoiceId,
                status = "open",
                currency = "USD",
                customer = "cus_1",
                description = body.description,
                attemptCount = 0,
                automaticAttemptCount = 0,
                billing = null,
                billingReason = null,
                createdAt = 1L,
                daysUntilDue = null,
                dueDate = null,
                hostedInvoiceUrl = null,
                invoicePdf = null,
                nextPaymentAttempt = null,
                number = null,
                paid = false,
                subtotal = 0.0,
                tax = 0.0,
                taxPercent = null,
                total = 0.0,
                lines = null,
                discount = null,
                metadata = body.metadata
            )
        )
        whenever(client.updateInvoice(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.updateInvoice(invoiceId, body)
        assertEquals(stub, out)

        verify(client).updateInvoice(
            invoiceId = eq(invoiceId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun deleteInvoice_passesPath_andHeaders() {
        val invoiceId = "inv_D"
        val stub = InvoiceDeleteResponse(
            RapydStatus(null, "OK", null, null, null),
            data = RapydDeletedItem(deleted = true, id = invoiceId)
        )
        whenever(client.deleteInvoice(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.deleteInvoice(invoiceId)
        assertEquals(stub, out)

        verify(client).deleteInvoice(
            invoiceId = eq(invoiceId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun voidInvoice_passesPath_andHeaders() {
        val invoiceId = "inv_V"
        val stub = InvoiceResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydInvoice(
                id = invoiceId,
                status = "void",
                currency = "USD",
                customer = "cus_1",
                description = null,
                attemptCount = 0,
                automaticAttemptCount = 0,
                billing = null,
                billingReason = null,
                createdAt = 1L,
                daysUntilDue = null,
                dueDate = null,
                hostedInvoiceUrl = null,
                invoicePdf = null,
                nextPaymentAttempt = null,
                number = null,
                paid = false,
                subtotal = 0.0,
                tax = 0.0,
                taxPercent = null,
                total = 0.0,
                lines = null,
                discount = null,
                metadata = null
            )
        )
        whenever(client.voidInvoice(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.voidInvoice(invoiceId)
        assertEquals(stub, out)

        verify(client).voidInvoice(
            invoiceId = eq(invoiceId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun finalizeInvoice_passesPath_andHeaders() {
        val invoiceId = "inv_F"
        val stub = InvoiceResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydInvoice(
                id = invoiceId,
                status = "open",
                currency = "USD",
                customer = "cus_1",
                description = null,
                attemptCount = 0,
                automaticAttemptCount = 0,
                billing = null,
                billingReason = null,
                createdAt = 1L,
                daysUntilDue = null,
                dueDate = null,
                hostedInvoiceUrl = null,
                invoicePdf = null,
                nextPaymentAttempt = null,
                number = null,
                paid = false,
                subtotal = 0.0,
                tax = 0.0,
                taxPercent = null,
                total = 0.0,
                lines = null,
                discount = null,
                metadata = null
            )
        )
        whenever(client.finalizeInvoice(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.finalizeInvoice(invoiceId)
        assertEquals(stub, out)

        verify(client).finalizeInvoice(
            invoiceId = eq(invoiceId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun payInvoice_passesPathBody_andHeaders() {
        val invoiceId = "inv_P"
        val body = PayInvoiceRequest(payment_method = "pm_1")
        val stub = InvoiceResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydInvoice(
                id = invoiceId,
                status = "paid",
                currency = "USD",
                customer = "cus_1",
                description = null,
                attemptCount = 1,
                automaticAttemptCount = 1,
                billing = null,
                billingReason = null,
                createdAt = 1L,
                daysUntilDue = null,
                dueDate = null,
                hostedInvoiceUrl = null,
                invoicePdf = null,
                nextPaymentAttempt = null,
                number = null,
                paid = true,
                subtotal = 10.0,
                tax = 0.0,
                taxPercent = null,
                total = 10.0,
                lines = null,
                discount = null,
                metadata = null
            )
        )
        whenever(client.payInvoice(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.payInvoice(invoiceId, body)
        assertEquals(stub, out)

        verify(client).payInvoice(
            invoiceId = eq(invoiceId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun markInvoiceUncollectible_passesPath_andHeaders() {
        val invoiceId = "inv_M"
        val stub = InvoiceResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydInvoice(
                id = invoiceId,
                status = "uncollectible",
                currency = "USD",
                customer = "cus_1",
                description = null,
                attemptCount = 0,
                automaticAttemptCount = 0,
                billing = null,
                billingReason = null,
                createdAt = 1L,
                daysUntilDue = null,
                dueDate = null,
                hostedInvoiceUrl = null,
                invoicePdf = null,
                nextPaymentAttempt = null,
                number = null,
                paid = false,
                subtotal = 0.0,
                tax = 0.0,
                taxPercent = null,
                total = 0.0,
                lines = null,
                discount = null,
                metadata = null
            )
        )
        whenever(client.markInvoiceUncollectible(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.markInvoiceUncollectible(invoiceId)
        assertEquals(stub, out)

        verify(client).markInvoiceUncollectible(
            invoiceId = eq(invoiceId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun getInvoiceLines_passesPath_andHeaders() {
        val invoiceId = "inv_Lines"
        val stub =
            InvoiceLinesResponse(RapydStatus(null, "OK", null, null, null), data = mapOf("items" to emptyList<Any>()))
        whenever(client.getInvoiceLines(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.getInvoiceLines(invoiceId)
        assertEquals(stub, out)

        verify(client).getInvoiceLines(
            invoiceId = eq(invoiceId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun getUpcomingInvoice_filtersOrdersParams_andHeaders() {
        val params = mapOf(
            "customer" to "cus_1",
            "subscription" to "sub_1",
            "limit" to "10",
            "cursor" to null,
            "from" to "2024-01-01"
        )
        val expectedOrdered = linkedMapOf(
            "customer" to "cus_1",
            "from" to "2024-01-01",
            "limit" to "10",
            "subscription" to "sub_1"
        )
        val stub = InvoiceResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydInvoice(
                id = "inv_upcoming",
                status = "draft",
                currency = "USD",
                customer = "cus_1",
                description = null,
                attemptCount = 0,
                automaticAttemptCount = 0,
                billing = null,
                billingReason = null,
                createdAt = 1L,
                daysUntilDue = null,
                dueDate = null,
                hostedInvoiceUrl = null,
                invoicePdf = null,
                nextPaymentAttempt = null,
                number = null,
                paid = false,
                subtotal = 0.0,
                tax = 0.0,
                taxPercent = null,
                total = 0.0,
                lines = null,
                discount = null,
                metadata = null
            )
        )
        whenever(client.getUpcomingInvoice(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.getUpcomingInvoice(params)
        assertEquals(stub, out)

        verify(client).getUpcomingInvoice(
            params = argThat { this.toList() == expectedOrdered.toList() },
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun getUpcomingInvoiceLines_filtersOrdersParams_andHeaders() {
        val params = mapOf(
            "customer" to "cus_1",
            "subscription" to "sub_1",
            "limit" to "10",
            "cursor" to null,
            "from" to "2024-01-01"
        )
        val expectedOrdered = linkedMapOf(
            "customer" to "cus_1",
            "from" to "2024-01-01",
            "limit" to "10",
            "subscription" to "sub_1"
        )
        val stub = UpcomingInvoiceLinesResponse(RapydStatus(null, "OK", null, null, null), data = emptyList())
        whenever(client.getUpcomingInvoiceLines(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.getUpcomingInvoiceLines(params)
        assertEquals(stub, out)

        verify(client).getUpcomingInvoiceLines(
            params = argThat { this.toList() == expectedOrdered.toList() },
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }
}
