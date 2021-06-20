package com.jaewoo.srs.core.exception.code

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {
    TOKEN_SIGNATURE_INVALID(401, "SECURITY-001", "Access Token SignatureException error"),
    TOKEN_EXPIRED(401, "SECURITY-002", "JWT Access Token Expired!"),
    TOKEN_AUTHENTICATION_ERROR(401, "SECURITY-003", "JWT Access Token Authentication Error!"),
    REFRESHTOKEN_SIGNATURE_INVALID(401, "SECURITY-004", "Refresh SignatureException error"),
    REFRESHTOKEN_EXPIRED(401, "SECURITY-005", "JWT Refresh Token Expired!"),
    REFRESHTOKEN_AUTHENTICATION_ERROR(401, "SECURITY-006", "JWT Refresh Token Authentication Error!"),
    REISSUE_TOKEN_SESSION_NOT_EXIST(401, "SECURITY-007", "Session does not exists while reissuing refresh token")
}