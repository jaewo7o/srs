package com.jaewoo.srs.core.web.response

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class SrsResponse(
    status: HttpStatus,
    val data: Any?
) : ResponseWrapper(status) {
}