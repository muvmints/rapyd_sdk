package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses

import com.ideazlab.jeie.muvmints.rapyd.sdk.Response
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydCustomer
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydDiscount
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus

@Response
data class CustomerResponse(
    val status: RapydStatus,
    val data: RapydCustomer
)

@Response
data class CustomerListResponse(
    val status: RapydStatus,
    val data: List<RapydCustomer>
)

@Response
data class DiscountResponse(
    val status: RapydStatus,
    val data: RapydDiscount
)

@Response
data class CustomerDiscountDeleteResponse(
    val status: RapydStatus,
    val data: RapydDeletedItem
)
