package com.jaewoo.srs.common.auth.domain.dto

import io.swagger.annotations.ApiParam
import javax.validation.constraints.NotBlank

data class LoginRequest(
    @ApiParam(value = "로그인ID", required = true, example = "jeawoo.jeong@gmail.com")
    @field:NotBlank
    val loginId: String,

    @ApiParam(value = "패스워드", required = true, example = "1234")
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