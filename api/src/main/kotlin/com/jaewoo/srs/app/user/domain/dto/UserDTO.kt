package com.jaewoo.srs.app.user.domain.dto

import com.jaewoo.srs.app.user.domain.entity.User
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

@ApiModel(description = "사용자생성요청")
data class CreateUserRequest(
    @ApiModelProperty(value = "사용자명", required = true, example = "정재우")
    @field:NotBlank
    val name: String,

    @ApiModelProperty(value = "핸드폰번호")
    val mobileNo: String,

    @ApiModelProperty(value = "언어코드")
    val languageCode: String,

    @ApiModelProperty(value = "Timezone")
    val timezoneName: String,

    @ApiModelProperty(value = "로그인ID", required = true, example = "jeawoo.jeong@gmail.com")
    @field:NotBlank
    val loginId: String,

    @ApiModelProperty(value = "패스워드", required = true, example = "1234")
    @field:NotBlank
    val password: String
) {
    fun toEntity() = User(
        name = name,
        mobileNo = mobileNo,
        languageCode = languageCode,
        timezoneName = timezoneName,
        loginId = loginId,
        password = password
    )
}

@ApiModel(description = "사용자수정요청")
data class UpdateUserRequest(
    @ApiModelProperty(value = "사용자명", required = true, example = "정재우")
    @field:NotBlank
    val name: String,

    @ApiModelProperty(value = "핸드폰번호")
    val mobileNo: String
)