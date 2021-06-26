package com.jaewoo.srs.common.message.domain.enums

import com.jaewoo.srs.common.message.domain.enum.MessageType
import com.jaewoo.srs.core.test.SpringTestSupport
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

    @Test
    fun `Json Deserialize Test`() {
        // given
        val messageTypeValue = "{ \"messageType\" : \"UM\" }"

        // when
        val messageType = objectMapper.readValue(messageTypeValue, MessageType::class.java)

        // then
        Assertions.assertThat(messageType).isNotNull()
        Assertions.assertThat(messageType).isEqualTo(MessageType.UI_MESSAGE)
    }
}