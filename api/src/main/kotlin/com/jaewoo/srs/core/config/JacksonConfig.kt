package com.jaewoo.srs.core.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
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
        javaTimeModule.addDeserializer(LocalDate::class.java, LocalDateDeserializer())
        javaTimeModule.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())

        objectMapper.registerModule(javaTimeModule)
        return objectMapper
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

    class LocalDateDeserializer : JsonDeserializer<LocalDate>() {
        @Throws(IOException::class)
        override fun deserialize(value: JsonParser, ctx: DeserializationContext?): LocalDate {
            return LocalDate.parse(value.valueAsString, DATE_FORMAT)
        }
    }

    class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
        @Throws(IOException::class)
        override fun deserialize(value: JsonParser, ctx: DeserializationContext?): LocalDateTime {
            return LocalDateTime.parse(value.valueAsString, DATETIME_FORMAT)
        }
    }
}