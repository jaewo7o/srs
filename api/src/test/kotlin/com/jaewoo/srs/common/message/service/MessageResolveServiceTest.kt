package com.jaewoo.srs.common.message.service

import com.jaewoo.srs.app.user.buildMessage
import com.jaewoo.srs.core.test.SpringTestSupport
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional
import java.util.*

internal class MessageResolveServiceTest(
    private val messageResolveService: MessageResolveService
) : SpringTestSupport() {

    @Test
    @Transactional
    fun `다국어메시지 변환 테스트 - key only`() {
        // given
        val saveMessage = save(buildMessage())

        // when
        val message = messageResolveService.resolveMessage(saveMessage.key)

        // then
        Assertions.assertThat(message).isEqualTo(saveMessage.contentsKo)
    }

    @Test
    @Transactional
    fun `다국어메시지 변환 테스트 - key + locale`() {
        // given
        val saveMessage = save(buildMessage())

        // when
        val message = messageResolveService.resolveMessage(saveMessage.key, Locale.ENGLISH)

        // then
        Assertions.assertThat(message).isEqualTo(saveMessage.contentsEn)
    }

    @Test
    @Transactional
    fun `다국어메시지 변환 테스트 - key + locale + arguments`() {
        // given
        val saveMessage = save(buildMessage(contentsKo = "message {0}"))

        // when
        val message = messageResolveService.resolveMessage(saveMessage.key, Locale.KOREAN, "test")

        // then
        Assertions.assertThat(message).isEqualTo("message test")
    }
}