package com.jaewoo.srs.core.web.response

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ErrorResponse {
    val isSuccess = false
    lateinit var error : ErrorDetail
}

data class ErrorDetail(
    val status: HttpStatus,
    val message: String,
    val stackTrace: String? = null
) {
    val code: Int
    val name: String
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    val timestamp: LocalDateTime

    init {
        code = status.value()
        name = status.name
        timestamp = LocalDateTime.now()
    }
}