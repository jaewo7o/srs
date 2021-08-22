package com.jaewoo.srs.core.enumerate

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
abstract class SrsAttributeConverter<E : BaseEnum> : AttributeConverter<E, String> {

    val enumClass: Class<E> = TODO()

    override fun convertToEntityAttribute(dbData: String): E {
        return enumClass.enumConstants.first { dbData.equals(it.getCode()) }
    }

    override fun convertToDatabaseColumn(attribute: E?): String {
        if (attribute == null) {
            return ""
        }

        return attribute?.getCode()
    }
}