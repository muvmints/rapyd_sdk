package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.ideazlab.jeie.muvmints.rapyd.sdk.TestUtils
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.ListPayoutMethodTypesResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.PayoutRequiredFieldsResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.*
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.PayoutMethodTypeClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class PayoutMethodTypeServiceTest {
    private val config = TestUtils.testConfig()
    private val objectMapper = TestUtils.objectMapper()
    private val client: PayoutMethodTypeClient = mock()
    private val service = PayoutMethodTypeService(config, client)

    @Test
    fun listPayoutMethodTypes_filtersAndSortsParams() {
        val input = mapOf(
            "z" to "9",
            "a" to "1",
            "empty" to "",
            "blank" to "   ",
            "nullish" to null
        )
        val expected = linkedMapOf("a" to "1", "z" to "9")

        val stub = ListPayoutMethodTypesResponse(
            RapydStatus(null, "OK", null, null, null),
            data = listOf(
                RapydPayoutMethodType(
                    type = "us_ach",
                    name = "ACH",
                    category = "bank_transfer",
                    image = null,
                    country = "US",
                    currency = "USD",
                    isCancelable = true,
                    isExpirable = null,
                    isLocationSpecified = null
                )
            )
        )
        whenever(client.listPayoutMethodTypes(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        val res = service.listPayoutMethodTypes(input)
        assertEquals(stub, res)

        verify(client).listPayoutMethodTypes(
            params = eq(expected as Map<String, String?>),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listPayoutMethodTypes_emptyParams_passesNull() {
        val stub = ListPayoutMethodTypesResponse(RapydStatus(null, "OK", null, null, null), emptyList())
        whenever(client.listPayoutMethodTypes(any(), any(), any(), any(), any(), any())).thenReturn(stub)

        service.listPayoutMethodTypes()

        verify(client).listPayoutMethodTypes(
            params = isNull(),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun getPayoutRequiredFields_uppercasesCountryAndCurrency_andPassesHeaders() {
        val payoutMethodType = "us_ach"
        val beneficiaryCountry = "us" // lower on purpose
        val entityType = "individual"
        val payoutAmount = 10
        val payoutCurrency = "usd" // lower on purpose

        val resp = PayoutRequiredFieldsResponse(
            RapydStatus(null, "OK", null, null, null),
            data = RapydPaymentRequiredFieldsData(
                type = payoutMethodType,
                fields = listOf(
                    RapydPaymentFieldDefinition(
                        name = "account_number",
                        type = "string",
                        regex = null,
                        description = null,
                        isRequired = true,
                        instructions = null
                    )
                ),
                paymentMethodOptions = emptyList<RapydPaymentOptionDefinition>(),
                paymentOptions = emptyList<RapydPaymentOptionDefinition>(),
                minimumExpirationSeconds = null,
                maximumExpirationSeconds = null
            )
        )
        whenever(
            client.getPayoutRequiredFields(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
            )
        ).thenReturn(resp)

        val out = service.getPayoutRequiredFields(
            payoutMethodType,
            beneficiaryCountry,
            entityType,
            payoutAmount,
            payoutCurrency
        )
        assertEquals(resp, out)

        verify(client).getPayoutRequiredFields(
            payoutMethodType = eq(payoutMethodType),
            beneficiaryCountry = eq(beneficiaryCountry.uppercase()),
            beneficiaryEntityType = eq(entityType),
            payoutAmount = eq(payoutAmount.toString()),
            payoutCurrency = eq(payoutCurrency.uppercase()),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }
}
