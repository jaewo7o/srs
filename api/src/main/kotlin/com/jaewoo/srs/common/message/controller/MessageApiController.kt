package com.jaewoo.srs.common.message.controller

import com.jaewoo.srs.common.message.domain.dto.CreateMessageRequest
import com.jaewoo.srs.common.message.domain.dto.SearchMessageRequest
import com.jaewoo.srs.common.message.domain.dto.SearchMessageResponse
import com.jaewoo.srs.common.message.domain.dto.UpdateMessageRequest
import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.common.message.service.MessageService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Api(
    tags = ["Message"],
    description = "다국어메시지관리"
)
@RestController
@RequestMapping(path = ["/api/messages", "/api/anonymous/messages"])
class MessageApiController(
    val messageService: MessageService
) {

    @ApiOperation(value = "다국어메세지 생성", notes = "새로운 다국어메시지를 생성한다.")
    @PostMapping
    fun createMessage(@Valid @RequestBody dto: CreateMessageRequest): Message = messageService.createMessage(dto)

    @ApiOperation(value = "다국어메세지 수정", notes = "ID에 맞는 다국어메시지를 수정한다.")
    @PutMapping("/{id}")
    fun updateMessage(
        @Valid @PathVariable id: Long,
        @Valid @RequestBody dto: UpdateMessageRequest
    ): Message {
        dto.id = id
        return messageService.updateMessage(dto)
    }

    @ApiOperation(value = "다국어메세지 삭제", notes = "ID에 맞는 다국어메시지를 삭제한다.")
    @DeleteMapping("/{id}")
    fun deleteMessage(
        @Valid @NotBlank @PathVariable id: Long,
    ) {
        return messageService.deleteMessage(id)
    }

    @ApiOperation(value = "다국어메세지 단건 조회", notes = "ID에 맞는 다국어메시지를 조회한다.")
    @GetMapping("/{id}")
    fun getMessage(
        @Valid @PathVariable id: Long,
    ): Message {
        return messageService.getMessage(id)
    }

    @ApiOperation(value = "다국어메세지 검색", notes = "검색조건에 맞는 다국어메시지를 조회한다.")
    @GetMapping
    fun searchMessage(dto: SearchMessageRequest, pageable: Pageable): Page<SearchMessageResponse> {
        return messageService.searchMessage(dto, pageable)
    }

}