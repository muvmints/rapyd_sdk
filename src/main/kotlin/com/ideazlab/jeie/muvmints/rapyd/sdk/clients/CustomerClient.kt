package com.ideazlab.jeie.muvmints.rapyd.sdk.clients

import com.ideazlab.jeie.muvmints.rapyd.sdk.api.requests.RapydCustomerRequest
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CustomerListResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.CustomerResponse
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.DiscountResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${rapyd.base-url}")
interface CustomerClient {

    @Get("/v1/customers{?starting_after,ending_before,limit}")
    fun listCustomers(
        @QueryValue("starting_after") startingAfter: String?,
        @QueryValue("ending_before") endingBefore: String?,
        @QueryValue("limit") limit: String?,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): CustomerListResponse

    @Post("/v1/customers")
    fun createCustomer(
        @Body body: RapydCustomerRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): CustomerResponse

    @Get("/v1/customers/{customerId}")
    fun retrieveCustomer(
        @PathVariable customerId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): CustomerResponse

    // Rapyd Update Customer is POST to /v1/customers/{customerId}
    @Post("/v1/customers/{customerId}")
    fun updateCustomer(
        @PathVariable customerId: String,
        @Body body: RapydCustomerRequest,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): CustomerResponse

    @Delete("/v1/customers/{customerId}")
    fun deleteCustomer(
        @PathVariable customerId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): CustomerResponse

    @Get("/v1/customers/discount/{discountId}")
    fun retrieveDiscount(
        @PathVariable discountId: String,
        @Header("access_key") accessKey: String,
        @Header("salt") salt: String,
        @Header("timestamp") timestamp: String,
        @Header("signature") signature: String,
        @Header("idempotency") idempotency: String
    ): DiscountResponse
}
