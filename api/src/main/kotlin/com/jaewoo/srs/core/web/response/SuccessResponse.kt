package com.jaewoo.srs.core.web.response

import org.springframework.http.HttpStatus

class SuccessResponse(
    val data: Any?
) : ResponseWrapper(HttpStatus.OK.value(), "OK", "Success")