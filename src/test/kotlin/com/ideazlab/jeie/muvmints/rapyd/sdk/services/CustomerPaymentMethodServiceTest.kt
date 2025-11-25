package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.ideazlab.jeie.muvmints.rapyd.sdk.TestUtils
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.AddCustomerPaymentMethodRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.UpdateCustomerPaymentMethodRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CustomerPaymentMethodResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CustomerPaymentMethodsListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.DeleteCustomerPaymentMethodResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.DeleteResult
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydCustomerPaymentMethod
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.CustomerPaymentMethodClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class CustomerPaymentMethodServiceTest {
    private val config = TestUtils.testConfig()
    private val objectMapper = TestUtils.objectMapper()
    private val client: CustomerPaymentMethodClient = mock()
    private val service = CustomerPaymentMethodService(config, objectMapper, client)

    @Test
    fun listCustomerPaymentMethods_filtersNulls_ordersKeys_andPassesHeaders() {
        val customerId = "cus_123"
        val params = mapOf(
            "type" to "card",
            "limit" to "10",
            "cursor" to null,
            "from" to "2024-01-01",
            "" to ""
        )
        val expectedOrdered = linkedMapOf(
            "from" to "2024-01-01",
            "limit" to "10",
            "type" to "card"
        )

        val stub = CustomerPaymentMethodsListResponse(
            RapydStatus(null, "OK", null, null, null),
            data = listOf(
                RapydCustomerPaymentMethod(
                    id = "pmt_1",
                    type = "card",
                    category = "card",
                    name = "Visa",
                    image = null,
                    token = null,
                    last4 = "4242",
                    fingerprintToken = null,
                    networkReferenceId = null,
                    redirectUrl = null,
                    webhookUrl = null,
                    supportingDocumentation = null,
                    nextAction = null,
                    metadata = null
                )
            )
        )

        whenever(client.listCustomerPaymentMethods(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listCustomerPaymentMethods(customerId, params)
        assertEquals(stub, out)

        verify(client).listCustomerPaymentMethods(
            customerId = eq(customerId),
            params = argThat { this.toList() == expectedOrdered.toList() },
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listCustomerPaymentMethods_whenParamsEmpty_passesNull_andHeaders() {
        val customerId = "cus_empty"
        val stub = CustomerPaymentMethodsListResponse(RapydStatus(null, "OK", null, null, null), data = emptyList())
        whenever(client.listCustomerPaymentMethods(any(), isNull(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listCustomerPaymentMethods(customerId, emptyMap())
        assertEquals(stub, out)

        verify(client).listCustomerPaymentMethods(
            customerId = eq(customerId),
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun addCustomerPaymentMethod_passesBody_andHeaders() {
        val customerId = "cus_add"
        val body = AddCustomerPaymentMethodRequest(data = mapOf("token" to "pm_token_1"))
        val stub = CustomerPaymentMethodResponse(
            RapydStatus(null, "OK", null, null, null),
            data = RapydCustomerPaymentMethod(
                id = "pmt_new",
                type = "card",
                category = "card",
                name = "Visa",
                image = null,
                token = "pm_token_1",
                last4 = "4242",
                fingerprintToken = null,
                networkReferenceId = null,
                redirectUrl = null,
                webhookUrl = null,
                supportingDocumentation = null,
                nextAction = null,
                metadata = null
            )
        )
        whenever(client.addCustomerPaymentMethod(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.addCustomerPaymentMethod(customerId, body)
        assertEquals(stub, out)

        verify(client).addCustomerPaymentMethod(
            customerId = eq(customerId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun retrieveCustomerPaymentMethod_passesPath_andHeaders() {
        val customerId = "cus_get"
        val pmtId = "pmt_get"
        val stub = CustomerPaymentMethodResponse(
            RapydStatus(null, "OK", null, null, null),
            data = RapydCustomerPaymentMethod(
                id = pmtId,
                type = "card",
                category = "card",
                name = "Visa",
                image = null,
                token = null,
                last4 = "1111",
                fingerprintToken = null,
                networkReferenceId = null,
                redirectUrl = null,
                webhookUrl = null,
                supportingDocumentation = null,
                nextAction = null,
                metadata = null
            )
        )
        whenever(client.retrieveCustomerPaymentMethod(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.retrieveCustomerPaymentMethod(customerId, pmtId)
        assertEquals(stub, out)

        verify(client).retrieveCustomerPaymentMethod(
            customerId = eq(customerId),
            pmtId = eq(pmtId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun updateCustomerPaymentMethod_passesPathBody_andHeaders() {
        val customerId = "cus_upd"
        val pmtId = "pmt_upd"
        val body = UpdateCustomerPaymentMethodRequest(name = "New Name", metadata = mapOf("k" to "v"))
        val stub = CustomerPaymentMethodResponse(
            RapydStatus(null, "OK", null, null, null),
            data = RapydCustomerPaymentMethod(
                id = pmtId,
                type = "card",
                category = "card",
                name = body.name,
                image = null,
                token = null,
                last4 = null,
                fingerprintToken = null,
                networkReferenceId = null,
                redirectUrl = null,
                webhookUrl = null,
                supportingDocumentation = null,
                nextAction = null,
                metadata = body.metadata
            )
        )
        whenever(client.updateCustomerPaymentMethod(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(
            stub
        )

        val out = service.updateCustomerPaymentMethod(customerId, pmtId, body)
        assertEquals(stub, out)

        verify(client).updateCustomerPaymentMethod(
            customerId = eq(customerId),
            pmtId = eq(pmtId),
            body = eq(body),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun deleteCustomerPaymentMethod_passesPath_andHeaders() {
        val customerId = "cus_del"
        val pmtId = "pmt_del"
        val stub = DeleteCustomerPaymentMethodResponse(
            RapydStatus(null, "OK", null, null, null),
            data = DeleteResult(id = pmtId, deleted = true)
        )
        whenever(client.deleteCustomerPaymentMethod(any(), any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.deleteCustomerPaymentMethod(customerId, pmtId)
        assertEquals(stub, out)

        verify(client).deleteCustomerPaymentMethod(
            customerId = eq(customerId),
            pmtId = eq(pmtId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }
}
