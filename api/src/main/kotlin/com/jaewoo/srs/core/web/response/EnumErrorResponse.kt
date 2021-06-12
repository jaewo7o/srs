package com.jaewoo.srs.core.web.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.jaewoo.srs.core.exception.code.ErrorCode

class EnumErrorResponse (
    @JsonIgnore
    val errorCode: ErrorCode,
    val stackTrace: String? = null
) : ResponseWrapper(errorCode.status, errorCode.code, errorCode.message){
}