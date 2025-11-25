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
data class RapydPayoutMethodType(
    val type: String,
    val name: String,
    val category: String?,
    val image: String?,
    val country: String?,
    val currency: String?,
    @param:JsonProperty(value = "is_cancelable")
    val isCancelable: Boolean? = null,
    @param:JsonProperty(value = "is_expirable")
    val isExpirable: Boolean? = null,
    @param:JsonProperty(value = "is_location_specified")
    val isLocationSpecified: Boolean? = null
)

@Serdeable
data class RapydCustomerPaymentMethod(
    val id: String?,
    val type: String?,
    val category: String?,
    val name: String?,
    val image: String?,
    val token: String?,
    @param:JsonProperty("last4")
    val last4: String? = null,
    @param:JsonProperty("fingerprint_token")
    val fingerprintToken: String? = null,
    @param:JsonProperty("network_reference_id")
    val networkReferenceId: String? = null,
    @param:JsonProperty("redirect_url")
    val redirectUrl: String? = null,
    @param:JsonProperty("webhook_url")
    val webhookUrl: String? = null,
    @param:JsonProperty("supporting_documentation")
    val supportingDocumentation: String? = null,
    @param:JsonProperty("next_action")
    val nextAction: Any? = null,
    val metadata: Map<String, Any?>? = null
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

// ---- Refunds ----

@Serdeable
data class RapydRefundEwalletShare(
    val amount: Double?,
    val ewallet: String?
)

@Serdeable
data class RapydRefund(
    val id: String,
    val amount: Double?,
    val currency: String?,
    @param:JsonProperty("created_at")
    val createdAt: Long? = null,
    val ewallets: List<RapydRefundEwalletShare>? = null,
    @param:JsonProperty("failure_reason")
    val failureReason: String? = null,
    @param:JsonProperty("fixed_side")
    val fixedSide: String? = null,
    @param:JsonProperty("fx_rate")
    val fxRate: String? = null,
    @param:JsonProperty("merchant_debited_amount")
    val merchantDebitedAmount: String? = null,
    @param:JsonProperty("merchant_debited_currency")
    val merchantDebitedCurrency: String? = null,
    @param:JsonProperty("merchant_reference_id")
    val merchantReferenceId: String? = null,
    val metadata: Map<String, Any?>? = null,
    val payment: String?,
    @param:JsonProperty("payment_created_at")
    val paymentCreatedAt: Long? = null,
    @param:JsonProperty("payment_method_type")
    val paymentMethodType: String? = null,
    @param:JsonProperty("proportional_refund")
    val proportionalRefund: Boolean? = null,
    val reason: String? = null,
    @param:JsonProperty("receipt_number")
    val receiptNumber: Long? = null,
    val status: String?,
    @param:JsonProperty("updated_at")
    val updatedAt: Long? = null
)

// ---- Disputes ----

@Serdeable
data class RapydDispute(
    val id: String?,
    // token is the dispute ID according to the OpenAPI, mapped to `token`
    val token: String?,
    val status: String?,
    val amount: Double?,
    val currency: String?,
    @param:JsonProperty("dispute_category")
    val disputeCategory: String?,
    @param:JsonProperty("dispute_reason_description")
    val disputeReasonDescription: String?,
    @param:JsonProperty("original_transaction_currency")
    val originalTransactionCurrency: String?,
    @param:JsonProperty("original_transaction_amount")
    val originalTransactionAmount: Double?,
    @param:JsonProperty("original_dispute_amount")
    val originalDisputeAmount: Double?,
    @param:JsonProperty("original_dispute_currency")
    val originalDisputeCurrency: String?,
    @param:JsonProperty("original_transaction_id")
    val originalTransactionId: String?,
    @param:JsonProperty("ewallet_id")
    val ewalletId: String?,
    @param:JsonProperty("central_processing_date")
    val centralProcessingDate: Long?,
    @param:JsonProperty("created_at")
    val createdAt: Long?,
    @param:JsonProperty("updated_at")
    val updatedAt: Long?,
    @param:JsonProperty("due_date")
    val dueDate: Long?,
    @param:JsonProperty("payment_method")
    val paymentMethod: String?,
    @param:JsonProperty("payment_method_data")
    val paymentMethodData: Map<String, Any?>?,
    val rate: Double?,
    val evidence: String?,
    @param:JsonProperty("evidence_reason_code")
    val evidenceReasonCode: String?,
    @param:JsonProperty("pre_dispute")
    val preDispute: Boolean?,
    val arn: String?
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

// ---- Group Payments ----

@Serdeable
data class RapydGroupPayment(
    val id: String?,
    val amount: Double?,
    @param:JsonProperty(value = "amount_to_replace")
    val amountToReplace: Double?,
    @param:JsonProperty(value = "cancel_reason")
    val cancelReason: String?,
    val country: String?,
    val currency: String?,
    val description: String?,
    val expiration: Long?,
    @param:JsonProperty(value = "merchant_reference_id")
    val merchantReferenceId: String?,
    val metadata: Map<String, Any?>?,
    val payments: List<RapydPayment>?,
    val status: String?
)



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


// ---- Address (Payment.Address) ----

@Serdeable
data class RapydAddress(
    val id: String?,
    val name: String?,
    @param:JsonProperty(value = "line_1")
    val line1: String?,
    @param:JsonProperty(value = "line_2")
    val line2: String?,
    @param:JsonProperty(value = "line_3")
    val line3: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val zip: String?,
    val canton: String?,
    val district: String?,
    @param:JsonProperty(value = "phone_number")
    val phoneNumber: String?,
    @param:JsonProperty(value = "created_at")
    val createdAt: Long?,
    val metadata: Map<String, Any?>?
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