package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.ideazlab.jeie.muvmints.rapyd.sdk.TestUtils
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.RapydWebhook
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.RapydWebhookAttempt
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.WebhookListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.WebhookResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.WebhookClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class WebhookServiceTest {

    private val config = TestUtils.testConfig()
    private val objectMapper = TestUtils.objectMapper()
    private val client: WebhookClient = mock()
    private val service = WebhookService(config, client)

    @Test
    fun listWebhooks_filtersAndSortsParams_andPassesSignedHeaders() {
        val input = mapOf(
            "b" to "2",
            "a" to "1",
            "empty" to "",
            "blank" to "   ",
            "nullish" to null
        )

        val expectedParams = linkedMapOf("a" to "1", "b" to "2")

        val stub = WebhookListResponse(
            status = RapydStatus(null, "OK", null, null, null),
            data = emptyList()
        )
        whenever(
            client.listWebhooks(
                params = any(),
                accessKey = any(),
                salt = any(),
                timestamp = any(),
                signature = any(),
                idempotency = any()
            )
        ).thenReturn(stub)

        val res = service.listWebhooks(input)

        assertEquals(stub, res)

        verify(client).listWebhooks(
            params = eq(expectedParams as Map<String, String?>),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listWebhooks_emptyParams_passesNullParams() {
        val stub = WebhookListResponse(
            status = RapydStatus(null, "OK", null, null, null),
            data = emptyList()
        )
        whenever(
            client.listWebhooks(
                params = any(),
                accessKey = any(),
                salt = any(),
                timestamp = any(),
                signature = any(),
                idempotency = any()
            )
        ).thenReturn(stub)

        service.listWebhooks()

        verify(client).listWebhooks(
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun getWebhook_callsClientWithSignedHeaders() {
        val webhookId = "wh_123"
        val stub = WebhookResponse(
            status = RapydStatus(null, "OK", null, null, null),
            data = RapydWebhook(emptyList<RapydWebhookAttempt>(), 0L, emptyMap(), 0L, 0L, "", "", 0)
        )
        whenever(
            client.getWebhook(any(), any(), any(), any(), any(), any())
        ).thenReturn(stub)

        val res = service.getWebhook(webhookId)
        assertEquals(stub, res)

        verify(client).getWebhook(
            webhook = eq(webhookId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun resendWebhook_callsClientWithSignedHeaders() {
        val webhookId = "wh_456"
        val stub = WebhookResponse(
            status = RapydStatus(null, "OK", null, null, null),
            data = RapydWebhook(emptyList<RapydWebhookAttempt>(), 0L, emptyMap(), 0L, 0L, "", "", 0)
        )
        whenever(
            client.resendWebhook(any(), any(), any(), any(), any(), any())
        ).thenReturn(stub)

        val res = service.resendWebhook(webhookId)
        assertEquals(stub, res)

        verify(client).resendWebhook(
            webhook = eq(webhookId),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    // No extra helpers – assertions are expressed inline with argThat
}
