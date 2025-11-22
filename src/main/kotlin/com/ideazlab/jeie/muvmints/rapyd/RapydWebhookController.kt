package com.ideazlab.jeie.muvmints.rapyd

import io.micronaut.http.*
import io.micronaut.http.annotation.*
import io.micronaut.http.HttpRequest

@Controller("/webhooks/rapyd")
class RapydWebhookController(
    private val webhookService: RapydWebhookService
) {

    @Post
    fun receiveWebhook(
        request: HttpRequest<Any>,
        @Header("signature") signature: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: Long,
        @Body rawBody: String
    ): HttpResponse<String> {
        return try {
            webhookService.handleWebhook(request, signature, salt, timestamp, rawBody)
            HttpResponse.ok("ok")
        } catch (ex: Exception) {
            HttpResponse.status<String>(HttpStatus.OK)
        }
    }
}