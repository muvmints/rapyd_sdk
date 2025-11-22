package com.ideazlab.jeie.muvmints.rapyd.sdk

data class SignedHeaders(
    val salt: String,
    val timestamp: Long,
    val signature: String,
    val idempotency: String
)