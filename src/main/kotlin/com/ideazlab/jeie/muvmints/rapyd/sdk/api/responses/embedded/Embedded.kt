package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class RapydStatus(
    @param:JsonProperty(value = "error_code")
    val errorCode: String?,
    val status: String,
    val message: String?,
    @param:JsonProperty(value = "response_code")
    val responseCode: String?,
    @param:JsonProperty(value = "operation_id")
    val operationId: String?
)

@Serdeable
data class RapydPaymentMethod(
    val type: String,
    val name: String,
    val category: String?,
    val image: String?,
    val country: String?,
    val currency: String?,
    @param:JsonProperty(value = "is_cancelable")
    val isCancelable: Boolean?
)

@Serdeable
data class RapydCreatePaymentRequest(
    val amount: Double,
    val currency: String,
    @param:JsonProperty(value = "payment_method")
    val paymentMethod: Map<String, Any?>? = null,
    val description: String? = null
)



@Serdeable
data class RapydPayment(
    val id: String,
    val amount: Double,
    val currency: String,
    val status: String,
    val description: String?
)

// ---- Wallets ----

@Serdeable
data class RapydWalletContactRequest(
    @param:JsonProperty(value = "first_name")
    val firsNtame: String,
    @param:JsonProperty(value = "last_name")
    val lastName: String,
    val email: String?,
    @param:JsonProperty(value = "phone_number")
    val phoneNumber: String?,
    @param:JsonProperty(value = "contact_type")
    val contactType: String = "personal",
    val country: String
)

@Serdeable
data class RapydCreateWalletRequest(
    val type: String,
    @param:JsonProperty(value = "ewallet_reference_id")
    val ewalletReferenceId: String,
    val contact: RapydWalletContactRequest
)

@Serdeable
data class RapydWalletContact(
    val id: String?,
    @param:JsonProperty(value = "first_name")
    val firstName: String?,
    @param:JsonProperty(value = "last_name")
    val lastName: String?,
    val email: String?,
    @param:JsonProperty(value = "phone_number")
    val phoneNumber: String?,
    @param:JsonProperty(value = "contact_type")
    val contactType: String?,
    val country: String?
)

@Serdeable
data class RapydWallet(
    val id: String,
    val status: String?,
    val type: String,
    @param:JsonProperty(value = "ewallet_reference_id")
    val ewalletReferenceId: String?,
    val contacts: List<RapydWalletContact>?
)



@Serdeable
data class RapydWalletAccount(
    val id: String?,
    val currency: String,
    val balance: Double
)



// ---- Payment Required Fields (from sample/payment_required_fields.json) ----



@Serdeable
data class RapydPaymentRequiredFieldsData(
    val type: String,
    val fields: List<RapydPaymentFieldDefinition>?,
    @param:JsonProperty(value = "payment_method_options")
    val paymentMethodOptions: List<RapydPaymentOptionDefinition>?,
    @param:JsonProperty(value = "payment_options")
    val paymentOptions: List<RapydPaymentOptionDefinition>?,
    @param:JsonProperty(value = "minimum_expiration_seconds")
    val minimumExpirationSeconds: Int?,
    @param:JsonProperty(value = "maximum_expiration_seconds")
    val maximumExpirationSeconds: Int?
)

@Serdeable
data class RapydPaymentFieldDefinition(
    val name: String,
    val type: String,
    val regex: String?,
    val description: String?,
    @param:JsonProperty(value = "is_required")
    val isRequired: Boolean?,
    val instructions: String?
)

@Serdeable
data class RapydPaymentOptionDefinition(
    val name: String,
    val type: String,
    val regex: String?,
    val description: String?,
    @param:JsonProperty(value = "is_required")
    val isRequired: Boolean?,
    @param:JsonProperty(value = "is_updatable")
    val isUpdatable: Boolean?
)

// ---- Customers ----

@Serdeable
data class RapydCustomerAddress(
    val name: String?,
    @param:JsonProperty(value = "line_1")
    val line1: String?,
    @param:JsonProperty(value = "line_2")
    val line2: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val zip: String?
)



@Serdeable
data class RapydCustomer(
    val id: String,
    val delinquent: Boolean?,
    val name: String?,
    val email: String?,
    @param:JsonProperty(value = "phone_number")
    val phoneNumber: String?,
    @param:JsonProperty(value = "business_vat_id")
    val businessVatId: String?,
    val metadata: Map<String, Any?>?,
    val description: String?,
    val addresses: List<RapydCustomerAddress>?,
    @param:JsonProperty(value = "created_at")
    val createdAt: Long?
)

@Serdeable
data class RapydDiscount(
    val id: String?,
    @param:JsonProperty(value = "amount_off")
    val amountOff: Double?,
    @param:JsonProperty(value = "percent_off")
    val percentOff: Double?,
    val currency: String?,
    val description: String?,
    val duration: String?,
    @param:JsonProperty(value = "duration_in_months")
    val durationInMonths: Int?,
    @param:JsonProperty(value = "discount_duration_in_uses")
    val discountDurationInUses: Int?,
    @param:JsonProperty(value = "discount_valid_until")
    val discountValidUntil: Long?,
    @param:JsonProperty(value = "discount_validity_in_months")
    val discountValidityInMonths: Int?,
    val valid: Boolean?,
    val created: Long?,
    val metadata: Map<String, Any?>?,
    @param:JsonProperty(value = "max_redemptions")
    val maxRedemptions: Int?,
    @param:JsonProperty(value = "redeem_by")
    val redeemBy: Long?,
    @param:JsonProperty(value = "times_redeemed")
    val timesRedeemed: Int?
)