package com.jaewoo.srs.core.web.response

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

open class ResponseWrapper(
    status: HttpStatus,
) {
    var isSuccess = true
    val timestamp: LocalDateTime

    init {
        timestamp = LocalDateTime.now()
        isSuccess = status.is2xxSuccessful
    }
}