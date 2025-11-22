package com.ideazlab.jeie.muvmints.rapyd.sdk

import com.ideazlab.jeie.muvmints.rapyd.sdk.utils.RapydSignatureUtil

open class BaseService {
     fun sign(method: String, pathWithQuery: String, bodyJson: String?,config: RapydConfig): SignedHeaders {
        val salt = RapydSignatureUtil.generateSalt()
        val ts = RapydSignatureUtil.currentTimestampSeconds()
        val signature = RapydSignatureUtil.signRequest(
            method = method,
            urlPathWithQuery = pathWithQuery,
            bodyJson = bodyJson,
            accessKey = config.accessKey,
            secretKey = config.secretKey,
            salt = salt,
            timestamp = ts
        )
        val idempotency = ts.toString() + salt
        return SignedHeaders(salt, ts, signature, idempotency)
    }
}