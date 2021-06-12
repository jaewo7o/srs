package com.jaewoo.srs.core.exception.code

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {
    TOKEN_SIGNATURE_INVALID(401, "SECURITY-001", "SignatureException error"),
    TOKEN_EXPIRED(401, "SECURITY-002", "JWT Token Expired!"),
    TOKEN_AUTHENTICATION_ERROR(401, "SECURITY-003", "JWT Token Authentication Error!")
}