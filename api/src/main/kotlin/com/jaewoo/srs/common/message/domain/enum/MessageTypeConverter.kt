package com.jaewoo.srs.common.message.domain.enum

import com.jaewoo.srs.core.enum.SrsAttributeConverter
import javax.persistence.Converter

@Converter
class MessageTypeConverter : SrsAttributeConverter<MessageType>() {
}