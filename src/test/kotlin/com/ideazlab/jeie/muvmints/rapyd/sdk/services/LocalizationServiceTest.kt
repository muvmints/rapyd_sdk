package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.TestUtils
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.*
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.LocalizationClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class LocalizationServiceTest {
    private val config: RapydConfig = TestUtils.testConfig()
    private val objectMapper: ObjectMapper = TestUtils.objectMapper()
    private val client: LocalizationClient = mock()
    private val service = LocalizationService(config, client)

    @Test
    fun getFxRate_uppercasesCurrencies_ordersParams_andPassesHeaders() {
        val action = "buy"
        val buy = "usd" // lower on purpose
        val sell = "eur" // lower on purpose
        val amount = 12.34
        val fixedSide = "buy"
        val date = "2025-01-01"

        val stub = GetFxRateResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydDailyRate(
                actionType = action,
                buyAmount = amount,
                buyCurrency = buy.uppercase(),
                date = date,
                fixedSide = fixedSide,
                rate = 0.9,
                sellAmount = null,
                sellCurrency = sell.uppercase()
            )
        )

        whenever(
            client.getFxRate(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        ).thenReturn(stub)

        val out = service.getFxRate(action, buy, sell, amount, fixedSide, date)
        assertEquals(stub, out)

        verify(client).getFxRate(
            actionType = eq(action),
            buyCurrency = eq(buy.uppercase()),
            sellCurrency = eq(sell.uppercase()),
            amount = eq(amount.toString()),
            fixedSide = eq(fixedSide),
            date = eq(date),
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listSupportedLanguages_passesSignedHeaders() {
        val stub = ListSupportedLanguagesApiResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydListSupportedLanguagesData(emptyList())
        )
        whenever(client.listSupportedLanguages(any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listSupportedLanguages()
        assertEquals(stub, out)

        verify(client).listSupportedLanguages(
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listCountries_passesSignedHeaders() {
        val stub = ListCountriesApiResponse(
            RapydStatus(null, "OK", null, null, null),
            RapydListCountriesData(emptyList())
        )
        whenever(client.listCountries(any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listCountries()
        assertEquals(stub, out)

        verify(client).listCountries(
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }

    @Test
    fun listCurrencies_passesSignedHeaders() {
        val stub = ListCurrenciesApiResponse(
            RapydStatus(null, "OK", null, null, null),
            data = emptyList()
        )
        whenever(client.listCurrencies(any(), any(), any(), any(), any())).thenReturn(stub)

        val out = service.listCurrencies()
        assertEquals(stub, out)

        verify(client).listCurrencies(
            accessKey = eq(config.accessKey),
            salt = any(),
            timestamp = any(),
            signature = any(),
            idempotency = any()
        )
    }
}
