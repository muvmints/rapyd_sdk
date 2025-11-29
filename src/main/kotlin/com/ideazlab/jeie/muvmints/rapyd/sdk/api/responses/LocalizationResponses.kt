package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.ideazlab.jeie.muvmints.rapyd.sdk.Response
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import io.micronaut.serde.annotation.Serdeable

@Response
data class GetFxRateResponse(
    val status: RapydStatus,
    val data: RapydDailyRate
)

@Response
data class ListSupportedLanguagesApiResponse(
    val status: RapydStatus,
    val data: RapydListSupportedLanguagesData
)

@Response
data class ListCountriesApiResponse(
    val status: RapydStatus,
    val data: List<RapydCountryInfo>
)

@Response
data class ListCurrenciesApiResponse(
    val status: RapydStatus,
    val data: List<RapydCurrencyInfo>
)

// ---- Embedded data structures for Localization ----

@Serdeable
data class RapydDailyRate(
    @param:JsonProperty("action_type")
    val actionType: String?,
    @param:JsonProperty("buy_amount")
    val buyAmount: Double?,
    @param:JsonProperty("buy_currency")
    val buyCurrency: String?,
    val date: String?,
    @param:JsonProperty("fixed_side")
    val fixedSide: String?,
    val rate: Double?,
    @param:JsonProperty("sell_amount")
    val sellAmount: Double?,
    @param:JsonProperty("sell_currency")
    val sellCurrency: String?
)

@Serdeable
data class RapydListSupportedLanguagesData(
    val languages: List<RapydSupportedLanguage>
)

@Serdeable
data class RapydSupportedLanguage(
    val name: String?,
    @param:JsonProperty("iso_alpha2") val isoAlpha2: String?
)

@Serdeable
data class RapydListCountriesData(
    val languages: List<RapydCountryInfo>
)

@Serdeable
data class RapydCountryInfo(
    @param:JsonProperty("currency_code") val currencyCode: String?,
    @param:JsonProperty("currency_name") val currencyName: String?,
    @param:JsonProperty("currency_sign") val currencySign: String?,
    val id: String?,
    @param:JsonProperty("iso_alpha2") val isoAlpha2: String?,
    @param:JsonProperty("iso_alpha3") val isoAlpha3: String?,
    val name: String?,
    @param:JsonProperty("phone_code") val phoneCode: String?
)

@Serdeable
data class RapydCurrencyInfo(
    val code: String?,
    @param:JsonProperty("digits_after_decimal_separator") val digitsAfterDecimalSeparator: String?,
    val name: String?,
    @param:JsonProperty("numeric_code") val numericCode: String?,
    val symbol: String?
)
