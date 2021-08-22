package com.jaewoo.srs.core.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.jaewoo.srs.core.enumerate.BaseEnum
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Configuration
class JacksonConfig {

    companion object {
        val DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper? {
        val objectMapper = ObjectMapper()
        val javaTimeModule = JavaTimeModule()

        javaTimeModule.addSerializer(LocalDate::class.java, LocalDateSerializer())
        javaTimeModule.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        javaTimeModule.addSerializer(ZonedDateTime::class.java, ZonedDateTimeSerializer())
        javaTimeModule.addDeserializer(LocalDate::class.java, LocalDateDeserializer())
        javaTimeModule.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())

        val simpleModule = SimpleModule()
        simpleModule.addSerializer(BaseEnum::class.java, BaseEnumSerializer())
        simpleModule.setDeserializerModifier(EnumDeserializerModifier())

        objectMapper.registerModule(javaTimeModule)
        objectMapper.registerModule(simpleModule)
        objectMapper.registerModule(KotlinModule())

        return objectMapper
    }

    class EnumDeserializerModifier : BeanDeserializerModifier() {
        override fun modifyEnumDeserializer(
            config: DeserializationConfig?,
            type: JavaType,
            beanDesc: BeanDescription?,
            deserializer: JsonDeserializer<*>?
        ): JsonDeserializer<*> {
            return super.modifyEnumDeserializer(config, type, beanDesc, deserializer)
        }
    }

    class BaseEnumSerializer : JsonSerializer<BaseEnum>() {
        override fun serialize(value: BaseEnum, gen: JsonGenerator?, p2: SerializerProvider?) {
            gen!!.writeString(value.getCode())
        }
    }

    class LocalDateSerializer : JsonSerializer<LocalDate>() {
        override fun serialize(value: LocalDate, gen: JsonGenerator?, p2: SerializerProvider?) {
            gen!!.writeString(value.format(DATE_FORMAT))
        }
    }

    class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
        override fun serialize(value: LocalDateTime, gen: JsonGenerator?, p2: SerializerProvider?) {
            gen!!.writeString(value.format(DATETIME_FORMAT))
        }
    }

    class ZonedDateTimeSerializer : JsonSerializer<ZonedDateTime>() {
        override fun serialize(value: ZonedDateTime, gen: JsonGenerator?, p2: SerializerProvider?) {
            gen!!.writeString(value.format(DATETIME_FORMAT))
        }
    }

    class LocalDateDeserializer : JsonDeserializer<LocalDate>() {
        @Throws(IOException::class)
        override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): LocalDate {
            return LocalDate.parse(parser.valueAsString, DATE_FORMAT)
        }
    }

    class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
        @Throws(IOException::class)
        override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): LocalDateTime {
            return LocalDateTime.parse(parser.valueAsString, DATETIME_FORMAT)
        }
    }

}
