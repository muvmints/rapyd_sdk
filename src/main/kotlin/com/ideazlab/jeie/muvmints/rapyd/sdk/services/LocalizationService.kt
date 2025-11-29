package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.GetFxRateResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.ListCountriesApiResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.ListCurrenciesApiResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.ListSupportedLanguagesApiResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.LocalizationClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(bean = RapydConfig::class)
class LocalizationService(
    private val config: RapydConfig,
    private val client: LocalizationClient
): BaseService() {

    fun getFxRate(
        actionType: String,
        buyCurrency: String,
        sellCurrency: String,
        amount: Double? = null,
        fixedSide: String? = null,
        date: String? = null
    ): GetFxRateResponse {
        val params = linkedMapOf(
            "action_type" to actionType,
            "amount" to amount?.toString(),
            "buy_currency" to buyCurrency.uppercase(),
            "date" to date,
            "fixed_side" to fixedSide,
            "sell_currency" to sellCurrency.uppercase()
        ).filterValues { !it.isNullOrBlank() }


        val query = params.entries.joinToString("&") { (k, v) -> "$k=$v" }
        val pathWithQuery = "/v1/fx_rates?${query}"
        val signed = sign("get", pathWithQuery, null, config)
        return client.getFxRate(
            actionType = actionType,
            buyCurrency = buyCurrency.uppercase(),
            sellCurrency = sellCurrency.uppercase(),
            amount = amount?.toString(),
            fixedSide = fixedSide,
            date = date,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun listSupportedLanguages(): ListSupportedLanguagesApiResponse {
        val path = "/v1/hosted/config/supported_languages"
        val signed = sign("get", path, null, config)
        return client.listSupportedLanguages(
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun listCountries(): ListCountriesApiResponse {
        val path = "/v1/data/countries"
        val signed = sign("get", path, null, config)
        return client.listCountries(
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun listCurrencies(): ListCurrenciesApiResponse {
        val path = "/v1/data/currencies"
        val signed = sign("get", path, null, config)
        return client.listCurrencies(
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
}
