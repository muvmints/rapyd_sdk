package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydCreateWalletRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CreateWalletResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.WalletAccountsResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.WalletClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
@Requires(bean = RapydConfig::class)
class WalletService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: WalletClient
): BaseService() {
    fun createWallet(body: RapydCreateWalletRequest): CreateWalletResponse {
        val path = "/v1/ewallets"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createWallet(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getWalletAccounts(ewalletId: String): WalletAccountsResponse {
        val path = "/v1/ewallets/${ewalletId}/accounts"
        val signed = sign("get", path, null, config)
        return client.getWalletAccounts(
            ewalletId = ewalletId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }
}