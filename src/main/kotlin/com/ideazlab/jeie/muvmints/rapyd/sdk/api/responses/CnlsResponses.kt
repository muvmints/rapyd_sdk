package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.ideazlab.jeie.muvmints.rapyd.sdk.Response
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CnlsSearchCriteria
import io.micronaut.serde.annotation.Serdeable

// Card Network Lookup Service (CNLS) responses

@Response
data class InitiateMerchantQueryResponse(
    val status: RapydStatus,
    val data: InitiateMerchantQueryData
)

@Serdeable
data class InitiateMerchantQueryData(
    @param:JsonProperty("operation_id")
    val operationId: String? = null,
    @param:JsonProperty("partner_merchant_reference")
    val partnerMerchantReference: String? = null,
    @param:JsonProperty("partner_query_reference")
    val partnerQueryReference: String? = null,
    val status: String? = null
)

@Response
data class RetrieveQueryResultsResponse(
    val status: RapydStatus,
    val data: RetrieveQueryResultsData
)

@Serdeable
data class RetrieveQueryResultsData(
    @param:JsonProperty("match_stats")
    val matchStats: CnlsMatchStats? = null,
    val matches: CnlsMatches? = null,
    @param:JsonProperty("query_info")
    val queryInfo: CnlsQueryInfo? = null,
    @param:JsonProperty("search_criteria")
    val searchCriteria: CnlsSearchCriteria? = null,
    val principals: List<CnlsPrincipalResult>? = null,
    val status: String? = null,
    @param:JsonProperty("matched_merchant")
    val matchedMerchant: CnlsMatchedMerchant? = null,
    @param:JsonProperty("registration_info")
    val registrationInfo: CnlsRegistrationInfo? = null
)

@Serdeable
data class CnlsMatchStats(
    @param:JsonProperty("query_match_count")
    val queryMatchCount: Double? = null,
    @param:JsonProperty("registered_match_count")
    val registeredMatchCount: Double? = null
)

@Serdeable
data class CnlsMatches(
    @param:JsonProperty("card_network")
    val cardNetwork: String? = null,
    @param:JsonProperty("exact_match")
    val exactMatch: List<String>? = null,
    @param:JsonProperty("match_type")
    val matchType: String? = null,
    @param:JsonProperty("partial_match")
    val partialMatch: List<String>? = null
)

@Serdeable
data class CnlsQueryInfo(
    @param:JsonProperty("partner_merchant_reference")
    val partnerMerchantReference: String? = null,
    @param:JsonProperty("partner_query_reference")
    val partnerQueryReference: String? = null,
    @param:JsonProperty("queried_merchant")
    val queriedMerchant: CnlsQueriedMerchantResult? = null
)

@Serdeable
data class CnlsQueriedMerchantResult(
    @param:JsonProperty("business_category")
    val businessCategory: String? = null,
    @param:JsonProperty("dba_name")
    val dbaName: String? = null,
    @param:JsonProperty("legal_name")
    val legalName: String? = null,
    val address: CnlsAddressResult? = null,
    @param:JsonProperty("phone_numbers")
    val phoneNumbers: List<String>? = null,
    @param:JsonProperty("is_ecommerce")
    val isEcommerce: Boolean? = null
)

@Serdeable
data class CnlsAddressResult(
    @param:JsonProperty("address_line_1")
    val addressLine1: String? = null,
    @param:JsonProperty("address_line_2")
    val addressLine2: String? = null,
    val city: String? = null,
    val country: String? = null,
    @param:JsonProperty("postal_code")
    val postalCode: String? = null
)

@Serdeable
data class CnlsPrincipalResult(
    @param:JsonProperty("first_name")
    val firstName: String? = null,
    @param:JsonProperty("middle_initial")
    val middleInitial: String? = null,
    @param:JsonProperty("last_name")
    val lastName: String? = null,
    val email: String? = null,
    @param:JsonProperty("phone_number")
    val phoneNumber: String? = null,
    val address: CnlsAddressResult? = null
)

@Serdeable
data class CnlsMatchedMerchant(
    @param:JsonProperty("business_category")
    val businessCategory: String? = null,
    @param:JsonProperty("dba_name")
    val dbaName: String? = null,
    @param:JsonProperty("legal_name")
    val legalName: String? = null,
    val address: CnlsAddressResult? = null,
    @param:JsonProperty("phone_numbers")
    val phoneNumbers: List<String>? = null,
    val mcc: List<String>? = null,
    val url: List<String>? = null
)

@Serdeable
data class CnlsRegistrationInfo(
    @param:JsonProperty("contract_end_date")
    val contractEndDate: Double? = null,
    @param:JsonProperty("contract_start_date")
    val contractStartDate: Double? = null,
    @param:JsonProperty("primary_registration_reason")
    val primaryRegistrationReason: String? = null,
    @param:JsonProperty("registered_by_acquirer_id")
    val registeredByAcquirerId: String? = null,
    @param:JsonProperty("registered_by_acquirer_name")
    val registeredByAcquirerName: String? = null,
    @param:JsonProperty("registered_by_acquirer_region")
    val registeredByAcquirerRegion: String? = null
)
