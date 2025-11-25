package com.ideazlab.jeie.muvmints.rapyd.sdk.utils

import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

object RapydSignatureUtil {

    private const val HMAC_ALGO = "HmacSHA256"
    private const val SALT_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    fun generateSalt(length: Int = 12): String =
        (1..length)
            .map { SALT_CHARS[Random.nextInt(SALT_CHARS.length)] }
            .joinToString("")

    fun currentTimestampSeconds(): Long =
        System.currentTimeMillis() / 1000L

    fun signRequest(
        method: String,
        urlPathWithQuery: String,
        bodyJson: String?,
        accessKey: String,
        secretKey: String,
        salt: String,
        timestamp: Long
    ): String {
        val httpMethod = method.lowercase()
        val bodyString = bodyJson ?: ""
        return generateSignature(httpMethod, urlPathWithQuery, salt, timestamp.toString(), accessKey, secretKey, bodyString)
    }
    fun generateSignature(httpMethod: String, urlPath: String, salt: String, timestamp: String, accessKey: String, secretKey: String, body: String): String {
        try {
            val toSign = httpMethod + urlPath + salt + timestamp + accessKey + secretKey + body
            val strHashCode = hmacDigest(toSign, secretKey, "HmacSHA256")
            val signature = Base64.getEncoder().encodeToString(strHashCode?.toByteArray())
            return signature
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
    fun hmacDigest(msg: String, keyString: String, algo: String): String? {
        var digest: String? = null
        try {
            val key = SecretKeySpec(keyString.toByteArray(StandardCharsets.US_ASCII), algo)
            val mac = Mac.getInstance(algo)
            mac.init(key)
            val bytes = mac.doFinal(msg.toByteArray(StandardCharsets.UTF_8))
            val hash = StringBuilder()
            for (i in bytes.indices) {
                val hex = Integer.toHexString(0xFF and bytes[i].toInt())
                if (hex.length == 1) {
                    hash.append('0')
                }
                hash.append(hex)
            }
            digest = hash.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return digest
    }
    fun signWebhook(
        urlPath: String,
        bodyJson: String,
        accessKey: String,
        secretKey: String,
        salt: String,
        timestamp: Long
    ): String {
        val toSign = buildString {
            append(urlPath)
            append(salt)
            append(timestamp.toString())
            append(accessKey)
            append(secretKey)
            append(bodyJson)
        }

        val mac = Mac.getInstance(HMAC_ALGO)
        mac.init(SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), HMAC_ALGO))
        val hashBytes = mac.doFinal(toSign.toByteArray(Charsets.UTF_8))

        return Base64.getEncoder().encodeToString(hashBytes)
    }
}