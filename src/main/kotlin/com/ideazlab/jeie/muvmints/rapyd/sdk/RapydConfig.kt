package com.ideazlab.jeie.muvmints.rapyd.sdk

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("rapyd")
class RapydConfig {
    lateinit var baseUrl: String
    lateinit var accessKey: String
    lateinit var secretKey: String
    var timeoutMs: Long = 15000
}