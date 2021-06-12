package com.jaewoo.srs.common.code.domain.dto

import com.jaewoo.srs.common.code.domain.entity.Code
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

class CodeDTO

@ApiModel(value = "코드 생성요청")
data class CreateCodeRequest(
    @ApiModelProperty(value = "그룹코드", required = true, example = "CM001")
    @field:NotBlank
    val groupCode: String,

    @ApiModelProperty(value = "코드", required = true, example = "A")
    @field:NotBlank
    val code: String,

    @ApiModelProperty(value = "코드 한글명", required = true, example = "메시지")
    @field:NotBlank
    val codeNameKo: String,

    @ApiModelProperty(value = "코드 영문명", required = false, example = "Message")
    val codeNameEn: String,

    @ApiModelProperty(value = "정렬순서", required = false, example = "1")
    val sortRank: Int? = 0
) {
    fun toEntity() = Code(
        groupCode = groupCode,
        code = code,
        codeNameKo = codeNameKo,
        codeNameEn = codeNameEn,
        sortRank = sortRank
    )
}

@ApiModel(value = "코드 수정요청")
data class UpdateCodeRequest(
    @ApiModelProperty(value = "그룹코드", required = true, example = "CM001")
    @field:NotBlank
    val groupCode: String,

    @ApiModelProperty(value = "코드", required = true, example = "A")
    @field:NotBlank
    val code: String,

    @ApiModelProperty(value = "코드 한글명", required = true, example = "메시지분류")
    @field:NotBlank
    val codeNameKo: String,

    @ApiModelProperty(value = "코드 영문명", required = false, example = "Message Type")
    val codeNameEn: String,

    @ApiModelProperty(value = "정렬순서", required = false, example = "1")
    val sortRank: Int? = 0
)