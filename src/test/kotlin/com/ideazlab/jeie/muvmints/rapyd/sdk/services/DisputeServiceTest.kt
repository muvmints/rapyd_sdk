package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.ideazlab.jeie.muvmints.rapyd.sdk.TestUtils
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.DisputeResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.ListDisputesResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydDispute
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.DisputeClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class DisputeServiceTest {
    private val config = TestUtils.testConfig()
    private val client: DisputeClient = mock()
    private val service = DisputeService(config, client)

    @Test
    fun listDisputes_filtersNulls_andOrdersKeys_andPassesHeaders() {
        val params = mapOf(
            "status" to "open",
            "limit" to "10",
            "cursor" to null,        // should be dropped
            "from" to "2024-01-01",
            "to" to "2024-12-31",
            "empty" to ""           // blank should be dropped
        )

        val expectedOrdered = linkedMapOf(
            "from" to "2024-01-01",
            "limit" to "10",
            "status" to "open",
            "to" to "2024-12-31"
        )

        val stub = ListDisputesResponse(
            RapydStatus(null, "OK", null, null, null),
            data = listOf(
                RapydDispute(
                    id = "disp_1",
                    token = "disp_1",
                    status = "open",
                    amount = 12.0,
                    currency = "USD",
                    disputeCategory = null,
                    disputeReasonDescription = null,
                    originalTransactionCurrency = null,
                    originalTransactionAmount = null,
                    originalDisputeAmount = null,
                    originalDisputeCurrency = null,
                    originalTransactionId = "pay_1",
                    ewalletId = null,
                    centralProcessingDate = null,
                    createdAt = null,
                    updatedAt = null,
                    dueDate = null,
                    paymentMethod = null,
                    paymentMethodData = null,
                    rate = null,
                    evidence = null,
                    evidenceReasonCode = null,
                    preDispute = null,
                    arn = null
                )
            )
        )

        whenever(client.listDisputes(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listDisputes(params)
        assertEquals(stub, out)

        verify(client).listDisputes(
            params = argThat { this.toList() == expectedOrdered.toList() },
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listDisputes_whenParamsEmpty_passesNullParams_andHeaders() {
        val stub = ListDisputesResponse(
            RapydStatus(null, "OK", null, null, null),
            data = emptyList()
        )
        whenever(client.listDisputes(isNull(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listDisputes(emptyMap())
        assertEquals(stub, out)

        verify(client).listDisputes(
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun retrieveDispute_passesPath_andSignedHeaders() {
        val id = "disp_123"
        val stub = DisputeResponse(
            RapydStatus(null, "OK", null, null, null),
            data = RapydDispute(
                id = id,
                token = id,
                status = "open",
                amount = 10.0,
                currency = "USD",
                disputeCategory = null,
                disputeReasonDescription = null,
                originalTransactionCurrency = null,
                originalTransactionAmount = null,
                originalDisputeAmount = null,
                originalDisputeCurrency = null,
                originalTransactionId = "pay_1",
                ewalletId = null,
                centralProcessingDate = null,
                createdAt = null,
                updatedAt = null,
                dueDate = null,
                paymentMethod = null,
                paymentMethodData = null,
                rate = null,
                evidence = null,
                evidenceReasonCode = null,
                preDispute = null,
                arn = null
            )
        )

        whenever(client.retrieveDispute(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.retrieveDispute(id)
        assertEquals(stub, out)

        verify(client).retrieveDispute(
            disputeId = eq(id),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }
}
