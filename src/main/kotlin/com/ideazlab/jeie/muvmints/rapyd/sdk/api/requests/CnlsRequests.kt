package com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

// Card Network Lookup Service (CNLS) requests

@Serdeable
data class InitiateMerchantQueryRequest(
    @param:JsonProperty("partner_merchant_reference")
    val partnerMerchantReference: String,
    @param:JsonProperty("partner_query_reference")
    val partnerQueryReference: String,
    @param:JsonProperty("search_criteria")
    val searchCriteria: CnlsSearchCriteria? = null,
    @param:JsonProperty("queried_merchant")
    val queriedMerchant: CnlsQueriedMerchant,
    // Some OpenAPI variants include this field in required; keep optional for compatibility
    @param:JsonProperty("reference_id")
    val referenceId: String? = null
)

@Serdeable
data class CnlsSearchCriteria(
    @param:JsonProperty("search_area")
    val searchArea: String? = null // global | local | regional
)

@Serdeable
data class CnlsQueriedMerchant(
    @param:JsonProperty("business_category")
    val businessCategory: String? = null,
    @param:JsonProperty("dba_name")
    val dbaName: String? = null,
    @param:JsonProperty("email")
    val email: String? = null,
    @param:JsonProperty("phone_number")
    val phoneNumber: String? = null,
    @param:JsonProperty("registration_id")
    val registrationId: String? = null,
    @param:JsonProperty("registration_country")
    val registrationCountry: String? = null,
    @param:JsonProperty("address")
    val address: CnlsAddress,
    @param:JsonProperty("mcc")
    val mcc: List<String>? = null,
    @param:JsonProperty("principals")
    val principals: List<CnlsPrincipal>? = null,
    @param:JsonProperty("is_ecommerce")
    val isEcommerce: Boolean? = null,
    @param:JsonProperty("legal_name")
    val legalName: String? = null,
    @param:JsonProperty("url")
    val url: List<String>? = null
)

@Serdeable
data class CnlsAddress(
    @param:JsonProperty("address_line_1")
    val addressLine1: String,
    @param:JsonProperty("address_line_2")
    val addressLine2: String,
    val city: String,
    val country: String,
    @param:JsonProperty("postal_code")
    val postalCode: String? = null
)

@Serdeable
data class CnlsPrincipal(
    @param:JsonProperty("first_name")
    val firstName: String? = null,
    @param:JsonProperty("middle_initial")
    val middleInitial: String? = null,
    @param:JsonProperty("last_name")
    val lastName: String? = null,
    @param:JsonProperty("email")
    val email: String? = null,
    @param:JsonProperty("phone_number")
    val phoneNumber: String? = null,
    @param:JsonProperty("address")
    val address: CnlsPrincipalAddress? = null
)

@Serdeable
data class CnlsPrincipalAddress(
    @param:JsonProperty("address_line_1")
    val addressLine1: String? = null,
    @param:JsonProperty("address_line_2")
    val addressLine2: String? = null,
    val city: String? = null,
    val country: String? = null,
    @param:JsonProperty("postal_code")
    val postalCode: String? = null
)
