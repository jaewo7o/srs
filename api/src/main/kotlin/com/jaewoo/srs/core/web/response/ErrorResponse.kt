package com.jaewoo.srs.core.web.response

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ErrorResponse (
    status: HttpStatus,
    val error : ErrorDetail
) : ResponseWrapper(status){
}

data class ErrorDetail(
    val message: String,
    val stackTrace: String? = null
) {
}