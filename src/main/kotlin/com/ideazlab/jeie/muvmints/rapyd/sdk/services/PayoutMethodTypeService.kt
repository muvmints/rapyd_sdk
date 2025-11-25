package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.ListPayoutMethodTypesResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.PayoutRequiredFieldsResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.PayoutMethodTypeClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(bean = RapydConfig::class)
class PayoutMethodTypeService(
    private val config: RapydConfig,
    private val client: PayoutMethodTypeClient
): BaseService() {

    fun listPayoutMethodTypes(params: Map<String, String?> = emptyMap()): ListPayoutMethodTypesResponse {
        val filtered = params.filterValues { !it.isNullOrBlank() }
        val ordered = linkedMapOf<String, String>()
        filtered.keys.sorted().forEach { k -> ordered[k] = filtered[k]!! }
        val query = if (ordered.isNotEmpty()) ordered.entries.joinToString("&") { (k, v) -> "$k=$v" } else null
        val pathWithQuery = buildString {
            append("/v1/payout_methods")
            if (!query.isNullOrBlank()) append("?").append(query)
        }
        val signed = sign("get", pathWithQuery, null, config)
        return client.listPayoutMethodTypes(
            params = if (ordered.isEmpty()) null else ordered,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getPayoutRequiredFields(
        payoutMethodType: String,
        beneficiaryCountry: String,
        beneficiaryEntityType: String,
        payoutAmount: Number,
        payoutCurrency: String
    ): PayoutRequiredFieldsResponse {
        val query = linkedMapOf(
            "beneficiary_country" to beneficiaryCountry.uppercase(),
            "beneficiary_entity_type" to beneficiaryEntityType,
            "payout_amount" to payoutAmount.toString(),
            "payout_currency" to payoutCurrency.uppercase()
        ).entries.joinToString("&") { (k, v) -> "$k=$v" }

        val pathWithQuery = "/v1/payout_methods/$payoutMethodType/required_fields?$query"
        val signed = sign("get", pathWithQuery, null, config)
        return client.getPayoutRequiredFields(
            payoutMethodType = payoutMethodType,
            beneficiaryCountry = beneficiaryCountry.uppercase(),
            beneficiaryEntityType = beneficiaryEntityType,
            payoutAmount = payoutAmount.toString(),
            payoutCurrency = payoutCurrency.uppercase(),
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
}
