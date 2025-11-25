package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses

import com.ideazlab.jeie.muvmints.rapyd.sdk.Response
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus

@Response
data class VerifyIdentityResponse(
    val status: RapydStatus,
    val data: Map<String, Any?>
)

@Response
data class VerifyIdDocumentsResponse(
    val status: RapydStatus,
    val data: List<Map<String, Any?>>
)

@Response
data class VerifyApplicationTypesByCountryResponse(
    val status: RapydStatus,
    val data: List<Map<String, Any?>>
)

@Response
data class VerifyApplicationStatusResponse(
    val status: RapydStatus,
    val data: Map<String, Any?>
)

@Response
data class VerifyHostedApplicationResponse(
    val status: RapydStatus,
    val data: Map<String, Any?>
)
