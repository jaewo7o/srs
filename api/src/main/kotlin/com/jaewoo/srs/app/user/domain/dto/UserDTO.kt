package com.jaewoo.srs.app.user.domain.dto

import com.jaewoo.srs.app.user.domain.entity.User
import javax.validation.constraints.NotBlank

class UserDTO

data class CreateUserRequest(
    @field:NotBlank
    val name: String,

    val mobileNo: String,

    @field:NotBlank
    val loginId: String,

    @field:NotBlank
    val password: String
) {
    fun toEntity() = User(
        name = name,
        mobileNo = mobileNo,
        loginId = loginId,
        password = password
    )
}

data class UpdateUserRequest(
    val name: String,
    val mobileNo: String
)