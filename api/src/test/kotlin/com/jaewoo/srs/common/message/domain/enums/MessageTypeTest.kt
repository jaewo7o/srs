package com.jaewoo.srs.common.message.domain.enums

import com.jaewoo.srs.SpringTestSupport
import com.jaewoo.srs.common.message.domain.enum.MessageType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class MessageTypeTest() : SpringTestSupport() {

    @Test
    fun `Json Serialize Test`() {
        // given
        val messageType = MessageType.UI_MESSAGE

        // when
        val serializeResult = objectMapper.writeValueAsString(messageType)

        // then
        Assertions.assertThat(serializeResult).isNotEmpty()
        Assertions.assertThat(serializeResult.contains(messageType.getCode())).isTrue()
    }
}