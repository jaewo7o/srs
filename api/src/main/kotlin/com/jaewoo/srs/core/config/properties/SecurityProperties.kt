package com.jaewoo.srs.core.config.properties

import org.hibernate.validator.constraints.Length
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt-security")
class SecurityProperties {
    @Length(min = 42, message = "Minimum length for the secret is 42.")
    val secretKey = "srs-backend-jwt-secret-key-jeawoo.jeong@gmail.com"

    // token
    // accesstoken 유효기간 15분
    // refreshtoken 유효기간 1일
    val accessTokenExpirationMiniute: Long = 1000L * 60 * 15
    val refreshTokenExpirationMiniute: Long = 1000L * 60 * 60 * 24

    val headerStringAccessToken = "X-AUTH-ACCESS-TOKEN"
    val headerStringRefreshToken = "X-AUTH-REFRESH-TOKEN"

    val securitySkipUrls = arrayListOf("/api/signin", "/api/signup")
}