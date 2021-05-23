package com.jaewoo.srs.common.message.controller

import com.jaewoo.srs.common.message.domain.dto.CreateMessageRequest
import com.jaewoo.srs.common.message.domain.dto.SearchMessageRequest
import com.jaewoo.srs.common.message.domain.dto.UpdateMessageRequest
import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.common.message.service.MessageService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/messages")
class MessageApiController(
    val messageService: MessageService
) {

    @PostMapping
    fun createMessage(@Valid @RequestBody dto: CreateMessageRequest): Message = messageService.createMessage(dto)

    @PutMapping("/{id}")
    fun updateMessage(
        @Valid @PathVariable id: Long,
        @Valid @RequestBody dto: UpdateMessageRequest
    ): Message {
        dto.id = id
        return messageService.updateMessage(dto)
    }

    @GetMapping("/{id}")
    fun getMessage(
        @Valid @PathVariable id: Long,
    ): Message {
        return messageService.getMessage(id)
    }

    @GetMapping
    fun searchMessage(dto: SearchMessageRequest, pageable: Pageable): Page<Message> {
        return messageService.searchMessage(dto, pageable)
    }

}