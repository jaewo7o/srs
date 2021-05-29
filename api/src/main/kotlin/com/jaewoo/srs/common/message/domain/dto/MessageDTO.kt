package com.jaewoo.srs.common.message.domain.dto

import com.jaewoo.srs.common.message.domain.entity.Message
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import javax.validation.constraints.NotBlank

class MessageDTO

@ApiModel(value = "다국어메시지 생성 DTO")
data class CreateMessageRequest(
    @ApiModelProperty(value = "다국어키", example = "add")
    @field:NotBlank
    val key: String,
    @ApiModelProperty(value = "국문", example = "추가")
    @field:NotBlank
    val contentsKo: String,
    @ApiModelProperty(value = "영문", example = "Add")
    @field:NotBlank
    val contentsEn: String
) {
    fun toEntitiy() = Message(
        key = key,
        contentsKo = contentsKo,
        contentsEn = contentsEn
    )
}

@ApiModel(value = "다국어메시지 수정 DTO")
data class UpdateMessageRequest(
    @ApiModelProperty(value = "다국어ID", hidden = true)
    var id: Long,
    @ApiModelProperty(value = "다국어키", required = true, example = "add")
    @field:NotBlank
    val key: String,
    @ApiModelProperty(value = "국문", required = true, example = "추가")
    @field:NotBlank
    val contentsKo: String,
    @ApiModelProperty(value = "영문", required = true, example = "Add")
    @field:NotBlank
    val contentsEn: String
)

@ApiModel(value = "다국어메시지 검색 DTO")
data class SearchMessageRequest(
    @ApiParam(value = "다국어키")
    var key: String?,
    @ApiParam(value = "검색어")
    var contents: String?
)