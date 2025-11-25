package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses

import com.ideazlab.jeie.muvmints.rapyd.sdk.Response
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydPayoutMethodType
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydPaymentRequiredFieldsData
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus

@Response
data class ListPayoutMethodTypesResponse(
    val status: RapydStatus,
    val data: List<RapydPayoutMethodType>
)

@Response
data class PayoutRequiredFieldsResponse(
    val status: RapydStatus,
    val data: RapydPaymentRequiredFieldsData
)
