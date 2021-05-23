package com.jaewoo.srs.common.message.controller

import com.jaewoo.srs.app.user.buildMessage
import com.jaewoo.srs.common.message.dao.MessageRepository
import com.jaewoo.srs.common.message.domain.dto.CreateMessageRequest
import com.jaewoo.srs.common.message.domain.dto.UpdateMessageRequest
import com.jaewoo.srs.core.test.SpringWebTestSupport
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.transaction.annotation.Transactional

internal class MessageApiControllerTest(
    val messageRepository: MessageRepository
) : SpringWebTestSupport() {

    val baseUrl = "/api/messages"

    @Test
    fun `메시지 생성`() {
        // given
        val dto = CreateMessageRequest(
            key = "MSGKEY",
            contentsKo = "CONTENTS1Ko",
            contentsEn = "CONTENTS1En"
        )

        // when
        mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
        }.andDo {
            print()
        }

        // then
        val findMessage = messageRepository.findByKey(dto.key).get()
        Assertions.assertThat(findMessage.key).isEqualTo(dto.key)
        Assertions.assertThat(findMessage.contentsKo).isEqualTo(dto.contentsKo)
        Assertions.assertThat(findMessage.contentsEn).isEqualTo(dto.contentsEn)
    }

    @Test
    @Transactional
    fun `메세지 수정`() {
        // given
        val saveMessage = save(buildMessage())
        var dto = UpdateMessageRequest(
            id = saveMessage.id!!,
            contentsKo = saveMessage.contentsKo + "수정",
            contentsEn = saveMessage.contentsEn + "Modify"
        )

        // when
        mockMvc.put("$baseUrl/${dto.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
        }.andDo { print() }

        // then
        val findMessage = messageRepository.findById(dto.id).get()
        Assertions.assertThat(findMessage.contentsKo).isEqualTo(dto.contentsKo)
        Assertions.assertThat(findMessage.contentsEn).isEqualTo(dto.contentsEn)
    }

    @Test
    @Transactional
    fun `메세지 단건 조회`() {
        // given
        val saveMessage = save(buildMessage())

        // when & then
        mockMvc.get(
            "$baseUrl/${saveMessage.id}"
        ).andExpect {
            status { isOk() }
            jsonPath("$.id") { value(saveMessage.id) }
            jsonPath("$.key") { value(saveMessage.key) }
            jsonPath("$.contentsKo") { value(saveMessage.contentsKo) }
            jsonPath("$.contentsEn") { value(saveMessage.contentsEn) }
        }.andDo { print() }
    }

    @Test
    @Transactional
    fun `메시지 목록 조회 - 페이징`() {
        // given
        (1..10).map {
            buildMessage(contentsKo = "message$it")
        }.also {
            saveAll(it)
        }

        val pageSize = 5
        val pageNumber = 0

        // when & then
        mockMvc.get(baseUrl) {
            param("page", pageNumber.toString())
            param("size", pageSize.toString())
            param("key", "key")
            param("contents", "message3")
        }.andExpect {
            status { isOk() }
            jsonPath("$.size") { value(pageSize) }
            jsonPath("$.number") { value(pageNumber) }
            jsonPath("$.numberOfElements") { value(1) }
        }.andDo { print() }
    }
}