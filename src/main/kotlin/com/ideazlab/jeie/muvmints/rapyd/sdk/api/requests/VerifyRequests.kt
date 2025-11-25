package com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@Introspected
data class VerifyIdentityRequest(
    val ewallet: String? = null,
    val contact: String? = null,
    val country: String? = null,
    val id_type: String? = null,
    val id_number: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val date_of_birth: String? = null,
    val additional_fields: Map<String, Any?>? = null
)

@Serdeable
@Introspected
data class CreateHostedVerifyApplicationRequest(
    val country: String? = null,
    val entity_type: String? = null,
    val reference_id: String? = null,
    val success_url: String? = null,
    val cancel_url: String? = null,
    val merchant_color: String? = null,
    val page_expiration: Int? = null,
    val metadata: Map<String, Any?>? = null,
    val applicant_details: Map<String, Any?>? = null,
    val prefill_details: Map<String, Any?>? = null
)
