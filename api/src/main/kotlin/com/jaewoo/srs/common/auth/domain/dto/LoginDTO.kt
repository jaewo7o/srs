package com.jaewoo.srs.common.auth.domain.dto

import javax.validation.constraints.NotBlank

class LoginDTO

data class LoginRequest(
    @field:NotBlank
    val loginId: String,

    @field:NotBlank
    val password: String
)

data class LoginResponse(
    val token: Token,
    val user: LoginUser
)

data class Token(
    val accessToken: String,
    val refreshToken: String
)

data class LoginUser(
    val name: String,
    val email: String,
    val mobileNo: String,
    val id: Long
)