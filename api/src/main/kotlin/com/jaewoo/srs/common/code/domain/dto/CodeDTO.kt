package com.jaewoo.srs.common.code.domain.dto

import com.jaewoo.srs.common.code.domain.entity.Code
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

@ApiModel(description = "코드 생성요청")
data class CreateCodeRequest(
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
    @ApiModelProperty(value = "그룹코드", hidden = true, required = false, example = "CM001")
    var groupCode: String = ""

    fun toEntity() = Code(
        groupCode = groupCode,
        code = code,
        codeNameKo = codeNameKo,
        codeNameEn = codeNameEn,
        sortRank = sortRank
    )
}

@ApiModel(description = "코드 수정요청")
data class UpdateCodeRequest(
    @ApiModelProperty(value = "코드 한글명", required = true, example = "메시지분류")
    @field:NotBlank
    val codeNameKo: String,

    @ApiModelProperty(value = "코드 영문명", required = false, example = "Message Type")
    val codeNameEn: String,

    @ApiModelProperty(value = "정렬순서", required = false, example = "1")
    val sortRank: Int? = 0
) {
    @ApiModelProperty(value = "그룹코드", hidden = true, required = true, example = "CM001")
    val groupCode: String = ""

    @ApiModelProperty(value = "코드", hidden = true, required = true, example = "A")
    val code: String = ""
}

@ApiModel(description = "코드 검색 결과")
data class SearchCodeResponse(
    @ApiModelProperty(value = "그룹코드")
    var groupCode: String = "",

    @ApiModelProperty(value = "코드")
    val code: String,

    @ApiModelProperty(value = "코드 한글명")
    val codeNameKo: String,

    @ApiModelProperty(value = "코드 영문명")
    val codeNameEn: String,

    @ApiModelProperty(value = "정렬순서")
    val sortRank: Int?
)