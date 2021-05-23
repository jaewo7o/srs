package com.jaewoo.srs.core.exception.handler

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ErrorResponse(
    status: HttpStatus,
    val message: String,
    var stackTrace: String? = null
) {
    val code: Int
    val name: String

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    val timestamp: LocalDateTime

    init {
        timestamp = LocalDateTime.now()
        code = status.value()
        name = status.name
    }
}