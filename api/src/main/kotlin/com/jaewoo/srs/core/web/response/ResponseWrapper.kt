package com.jaewoo.srs.core.web.response

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

open class ResponseWrapper(
    status: HttpStatus,
) {
    var isSuccess = true
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    val timestamp: LocalDateTime

    init {
        timestamp = LocalDateTime.now()
        isSuccess = status.is2xxSuccessful
    }
}