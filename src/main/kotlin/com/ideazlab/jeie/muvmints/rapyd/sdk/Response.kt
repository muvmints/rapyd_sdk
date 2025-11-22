package com.ideazlab.jeie.muvmints.rapyd.sdk

import com.fasterxml.jackson.annotation.JsonInclude
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@Introspected
@JsonInclude(JsonInclude.Include.NON_NULL)
annotation class Response