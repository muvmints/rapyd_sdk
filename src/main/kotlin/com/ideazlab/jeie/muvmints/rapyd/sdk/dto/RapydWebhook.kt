package com.ideazlab.jeie.muvmints.rapyd.sdk.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class RapydWebhookEnvelope(
    val id: String?,
    val type: String?,
    val data: RapydWebhookData?
)

@Serdeable
data class RapydWebhookData(
    val id: String?,
    val status: String?,
    val amount: Double?,
    val currency: String?,
    val description: String?
)