package com.jaewoo.srs.core.web.response

import org.springframework.http.HttpStatus

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