package com.jaewoo.srs.core.enumerate

import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import java.util.*
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
abstract class SrsAttributeConverter<E : BaseEnum> : AttributeConverter<E, String> {

    protected abstract fun getValues(): Array<E>

    override fun convertToEntityAttribute(dbData: String): E {
        return Arrays.stream(getValues())
            .filter {
                dbData.equals(it.getCode())
            }
            .findFirst()
            .orElseThrow { SrsDataNotFoundException() }
    }

    override fun convertToDatabaseColumn(attribute: E?): String {
        return attribute?.getCode() ?: ""
    }
}