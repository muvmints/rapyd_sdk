package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydCreateWalletRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CreateWalletResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.WalletAccountsResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface WalletClient {


    @Post("/v1/ewallets")
    fun createWallet(
        @Body body: RapydCreateWalletRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): CreateWalletResponse

    @Get("/v1/ewallets/{ewalletId}/accounts")
    fun getWalletAccounts(
        @PathVariable ewalletId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): WalletAccountsResponse
}