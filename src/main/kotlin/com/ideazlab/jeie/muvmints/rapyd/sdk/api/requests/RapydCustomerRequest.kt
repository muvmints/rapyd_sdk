package com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests

import com.ideazlab.jeie.muvmints.annotations.Request

@Request
data class RapydCustomerRequest(
    val name: String,
    val email: String? = "",
//    @param:JsonProperty(value = "phone_number")
//    val phoneNumber: String = "",
//    @param:JsonProperty(value = "business_vat_id")
//    val businessVatId: String = "",
//   // val metadata: Map<String, Any?>? = null,
//    val addresses: List<RapydCustomerAddress>? = null,
//  //  val description: String = "",
//  //  val coupon: String = "",
)