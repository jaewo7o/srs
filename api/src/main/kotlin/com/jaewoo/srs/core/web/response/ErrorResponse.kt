package com.jaewoo.srs.core.web.response

class ErrorResponse (
    status: Int,
    message: String,
    val stackTrace: String? = null
) : ResponseWrapper(status, "", message){
}