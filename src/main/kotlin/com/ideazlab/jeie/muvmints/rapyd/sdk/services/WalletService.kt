package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.CreateContactRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.UpdateWalletRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.*
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydCreateWalletRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.WalletClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(bean = RapydConfig::class)
class WalletService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: WalletClient
): BaseService() {
    fun listWallets(
        phoneNumber: String? = null,
        email: String? = null,
        ewalletReferenceId: String? = null,
        pageNumber: Number? = null,
        pageSize: Number? = null,
        type: String? = null,
        minBalance: Number? = null,
        currency: String? = null
    ): WalletListResponse {
        val query = buildString {
            val params = mutableListOf<String>()
            if (!phoneNumber.isNullOrBlank()) params.add("phone_number=$phoneNumber")
            if (!email.isNullOrBlank()) params.add("email=$email")
            if (!ewalletReferenceId.isNullOrBlank()) params.add("ewallet_reference_id=$ewalletReferenceId")
            if (pageNumber != null) params.add("page_number=$pageNumber")
            if (pageSize != null) params.add("page_size=$pageSize")
            if (!type.isNullOrBlank()) params.add("type=$type")
            if (minBalance != null) params.add("min_balance=$minBalance")
            if (!currency.isNullOrBlank()) params.add("currency=$currency")
            if (params.isNotEmpty()) append("?" + params.joinToString("&"))
        }
        val path = "/v1/ewallets$query"
        val signed = sign("get", path, null, config)
        return client.listWallets(
            phoneNumber = phoneNumber,
            email = email,
            ewalletReferenceId = ewalletReferenceId,
            pageNumber = pageNumber,
            pageSize = pageSize,
            type = type,
            minBalance = minBalance,
            currency = currency,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
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

    fun retrieveWallet(ewalletToken: String): CreateWalletResponse {
        val path = "/v1/ewallets/$ewalletToken"
        val signed = sign("get", path, null, config)
        return client.retrieveWallet(
            ewalletToken = ewalletToken,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun updateWallet(ewalletToken: String, body: UpdateWalletRequest): CreateWalletResponse {
        val path = "/v1/ewallets/$ewalletToken"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.updateWallet(
            ewalletToken = ewalletToken,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun deleteWallet(ewalletToken: String): WalletActionResponse {
        val path = "/v1/ewallets/$ewalletToken"
        val signed = sign("delete", path, null, config)
        return client.deleteWallet(
            ewalletToken = ewalletToken,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun changeWalletStatus(ewalletToken: String, status: String): WalletActionResponse {
        val path = "/v1/ewallets/$ewalletToken/statuses/$status"
        val signed = sign("post", path, null, config)
        return client.changeWalletStatus(
            ewalletToken = ewalletToken,
            status = status,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    // ---- eWallet.Contact ----

    fun listContacts(ewalletId: String): ContactListResponse {
        val path = "/v1/ewallets/$ewalletId/contacts"
        val signed = sign("get", path, null, config)
        return client.listContacts(
            ewalletId = ewalletId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun createContact(ewalletId: String, body: CreateContactRequest): ContactResponse {
        val path = "/v1/ewallets/$ewalletId/contacts"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.createContact(
            ewalletId = ewalletId,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getContact(ewalletId: String, contactId: String): ContactResponse {
        val path = "/v1/ewallets/$ewalletId/contacts/$contactId"
        val signed = sign("get", path, null, config)
        return client.getContact(
            ewalletId = ewalletId,
            contactId = contactId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun updateContact(ewalletId: String, contactId: String, body: Map<String, Any?>): ContactResponse {
        val path = "/v1/ewallets/$ewalletId/contacts/$contactId"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.updateContact(
            ewalletId = ewalletId,
            contactId = contactId,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun deleteContact(ewalletId: String, contactId: String): DeleteContactResponse {
        val path = "/v1/ewallets/$ewalletId/contacts/$contactId"
        val signed = sign("delete", path, null, config)
        return client.deleteContact(
            ewalletId = ewalletId,
            contactId = contactId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun getContactComplianceLevels(ewalletId: String, contactId: String): ComplianceLevelsResponse {
        val path = "/v1/ewallets/$ewalletId/contacts/$contactId/compliance_levels"
        val signed = sign("get", path, null, config)
        return client.getContactComplianceLevels(
            ewalletId = ewalletId,
            contactId = contactId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
}