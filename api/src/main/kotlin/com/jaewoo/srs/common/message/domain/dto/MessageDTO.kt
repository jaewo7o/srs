package com.jaewoo.srs.common.message.domain.dto

import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.common.message.domain.enum.MessageType
import com.querydsl.core.annotations.QueryProjection
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@ApiModel(description = "다국어메시지 생성")
data class CreateMessageRequest(
    @ApiModelProperty(value = "다국어키", example = "add")
    @field:NotBlank
    val key: String,

    @ApiModelProperty(value = "메시지유형", example = "TERM")
    @field:NotNull
    val messageType: MessageType,

    @ApiModelProperty(value = "국문", example = "추가")
    @field:NotBlank
    val contentsKo: String,

    @ApiModelProperty(value = "영문", example = "Add")
    @field:NotBlank
    val contentsEn: String
) {
    fun toEntitiy() = Message(
        key = key,
        messageType = messageType,
        contentsKo = contentsKo,
        contentsEn = contentsEn
    )
}

@ApiModel(description = "다국어메시지 수정")
data class UpdateMessageRequest(
    @ApiModelProperty(value = "다국어키", required = true, example = "add")
    @field:NotBlank
    val key: String,

    @ApiModelProperty(value = "메시지유형", example = "TERM")
    @field:NotNull
    val messageType: MessageType,

    @ApiModelProperty(value = "국문", required = true, example = "추가")
    @field:NotBlank
    val contentsKo: String,

    @ApiModelProperty(value = "영문", required = true, example = "Add")
    @field:NotBlank
    val contentsEn: String
) {
    @ApiModelProperty(value = "다국어ID", hidden = true)
    var id: Long = 0
}

@ApiModel(description = "다국어메시지 검색요청")
data class SearchMessageRequest(
    @ApiParam(value = "다국어키")
    var key: String?,
    @ApiParam(value = "검색어")
    var contents: String?
)

@ApiModel(description = "다국어메시지 검색결과")
data class SearchMessageResponse @QueryProjection constructor(
    @ApiModelProperty(value = "다국어ID", example = "1")
    val id: Long,

    @ApiModelProperty(value = "다국어키", example = "add")
    val key: String,

    @ApiModelProperty(value = "메시지유형", example = "TERM")
    val messageType: String,

    @ApiModelProperty(value = "국문", example = "추가")
    val contentsKo: String,

    @ApiModelProperty(value = "영문", example = "Add")
    val contentsEn: String,

    @ApiModelProperty(value = "최근수정자명", example = "Hong Gil Dong")
    val updatedUserName: String,

    @ApiModelProperty(value = "최근수정일시")
    val updatedAt: LocalDateTime
)