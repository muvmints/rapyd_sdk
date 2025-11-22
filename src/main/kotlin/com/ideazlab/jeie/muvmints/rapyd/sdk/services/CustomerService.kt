package com.ideazlab.jeie.muvmints.rapyd.sdk.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ideazlab.jeie.muvmints.rapyd.sdk.BaseService
import com.ideazlab.jeie.muvmints.rapyd.sdk.RapydConfig
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.RapydCustomerRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CustomerListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CustomerResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.DiscountResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.clients.CustomerClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(bean = RapydConfig::class)
class CustomerService(
    private val config: RapydConfig,
    private val objectMapper: ObjectMapper,
    private val client: CustomerClient
): BaseService() {

    fun listCustomers(startingAfter: String?, endingBefore: String?, limit: String?): CustomerListResponse {
        val query = buildString {
            val params = mutableListOf<String>()
            if (!startingAfter.isNullOrBlank()) params.add("starting_after=$startingAfter")
            if (!endingBefore.isNullOrBlank()) params.add("ending_before=$endingBefore")
            if (!limit.isNullOrBlank()) params.add("limit=$limit")
            if (params.isNotEmpty()) append("?" + params.joinToString("&"))
        }
        val path = "/v1/customers$query"
        val signed = sign("get", path, null, config)
        return client.listCustomers(
            startingAfter = startingAfter,
            endingBefore = endingBefore,
            limit = limit,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun createCustomer(body: RapydCustomerRequest): CustomerResponse {
        val path = "/v1/customers"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/").replace("null", "''")
        val signed = sign("post", path, jsonBody, config)
        return client.createCustomer(
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun retrieveCustomer(customerId: String): CustomerResponse {
        val path = "/v1/customers/$customerId"
        val signed = sign("get", path, null, config)
        return client.retrieveCustomer(
            customerId = customerId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun updateCustomer(customerId: String, body: RapydCustomerRequest): CustomerResponse {
        val path = "/v1/customers/$customerId"
        val jsonBody = objectMapper.writeValueAsString(body).replace("\\/", "/")
        val signed = sign("post", path, jsonBody, config)
        return client.updateCustomer(
            customerId = customerId,
            body = body,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun deleteCustomer(customerId: String): CustomerResponse {
        val path = "/v1/customers/$customerId"
        val signed = sign("delete", path, null, config)
        return client.deleteCustomer(
            customerId = customerId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }

    fun retrieveDiscount(discountId: String): DiscountResponse {
        val path = "/v1/customers/discount/$discountId"
        val signed = sign("get", path, null, config)
        return client.retrieveDiscount(
            discountId = discountId,
            accessKey = config.accessKey,
            salt = signed.salt,
            timestamp = signed.timestamp.toString(),
            signature = signed.signature,
            idempotency = signed.idempotency
        )
    }
}
