package com.jaewoo.srs.common.code.domain.dto

import com.jaewoo.srs.common.code.domain.entity.GroupCode
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import javax.validation.constraints.NotBlank

class GroupCodeDTO

@ApiModel(value = "그룹코드 생성요청")
data class CreateGroupCodeRequest(
    @ApiModelProperty(value = "그룹코드", required = true, example = "CM001")
    @field:NotBlank
    val groupCode: String,

    @ApiModelProperty(value = "그룹코드 한글명", required = true, example = "메시지분류")
    @field:NotBlank
    val groupCodeNameKo: String,

    @ApiModelProperty(value = "그룹코드 영문명", required = false, example = "Message Type")
    val groupCodeNameEn: String
) {
    fun toEntity() = GroupCode(
        groupCode = groupCode,
        groupCodeNameKo = groupCodeNameKo,
        groupCodeNameEn = groupCodeNameEn
    )
}

@ApiModel(value = "그룹코드 수정요청")
data class UpdateGroupCodeRequest(
    @ApiModelProperty(value = "그룹코드 한글명", required = true, example = "메시지분류")
    @field:NotBlank
    val groupCodeNameKo: String,

    @ApiModelProperty(value = "그룹코드 영문명", required = false, example = "Message Type")
    val groupCodeNameEn: String
)

@ApiModel(value = "그룹코드 검색")
data class SearchGroupCodeRequest(
    @ApiParam(value = "검색어")
    var name: String?
)