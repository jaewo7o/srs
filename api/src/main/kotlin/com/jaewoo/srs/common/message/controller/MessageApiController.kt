package com.jaewoo.srs.common.message.controller

import com.jaewoo.srs.common.message.domain.dto.CreateMessageRequest
import com.jaewoo.srs.common.message.domain.dto.SearchMessageRequest
import com.jaewoo.srs.common.message.domain.dto.UpdateMessageRequest
import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.common.message.service.MessageService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(value = "다국어메지시 REST API")
@RestController
@RequestMapping("/api/messages")
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

    @ApiOperation(value = "다국어메세지 단건 조회", notes = "ID에 맞는 다국어메시지를 조회한다.")
    @GetMapping("/{id}")
    fun getMessage(
        @Valid @PathVariable id: Long,
    ): Message {
        return messageService.getMessage(id)
    }

    @ApiOperation(value = "다국어메세지 검색", notes = "검색조건에 맞는 다국어메시지를 조회한다.")
    @GetMapping
    fun searchMessage(dto: SearchMessageRequest, pageable: Pageable): Page<Message> {
        return messageService.searchMessage(dto, pageable)
    }

}