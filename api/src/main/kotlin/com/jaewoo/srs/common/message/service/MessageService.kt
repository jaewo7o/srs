package com.jaewoo.srs.common.message.service

import com.jaewoo.srs.common.message.dao.MessagePredicator
import com.jaewoo.srs.common.message.dao.MessageRepository
import com.jaewoo.srs.common.message.dao.MessageRepositorySupport
import com.jaewoo.srs.common.message.domain.dto.CreateMessageRequest
import com.jaewoo.srs.common.message.domain.dto.SearchMessageRequest
import com.jaewoo.srs.common.message.domain.dto.UpdateMessageRequest
import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import com.jaewoo.srs.core.exception.SrsRuntimeException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageService(
    val messageRepository: MessageRepository,
    val messageRepositorySupport: MessageRepositorySupport
) {
    @Transactional
    fun createMessage(dto: CreateMessageRequest): Message {
        val message = dto.toEntitiy()

        val isExists = this.messageRepository.existsByKey(message.key)
        if (isExists) {
            throw SrsRuntimeException("MSG0002", message.key)
        }

        return messageRepository.save(message)
    }

    @Transactional
    fun updateMessage(dto: UpdateMessageRequest): Message {
        val message = this.getMessage(dto.id).also {
            it.messageType = dto.messageType
            it.contentsKo = dto.contentsKo
            it.contentsEn = dto.contentsEn
        }

        return messageRepository.save(message)
    }

    fun getMessage(id: Long): Message {
        return messageRepository.findById(id)
            .orElseThrow { SrsDataNotFoundException() }
    }

    fun getMessage(key: String): Message {
        return messageRepository.findByKey(key)
            .orElseThrow { SrsDataNotFoundException() }
    }

    fun searchMessage(dto: SearchMessageRequest, pageable: Pageable): Page<Message> {
        val predicate = MessagePredicator()
            .key(dto.key)
            .contents(dto.contents)
            .value()

        return messageRepositorySupport.findAllPage(predicate, pageable)
    }

    @Transactional
    fun deleteMessage(id: Long) {
        messageRepository.deleteById(id)
    }
}
