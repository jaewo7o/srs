package com.jaewoo.srs.core.web.response

import org.springframework.http.HttpStatus

class SuccessResponse(
    status: HttpStatus,
    val data: Any?
) : ResponseWrapper(status) {
}