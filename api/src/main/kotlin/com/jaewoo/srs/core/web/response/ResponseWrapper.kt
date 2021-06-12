package com.jaewoo.srs.core.web.response

import java.time.LocalDateTime

open class ResponseWrapper(
    val status: Int,
    val code: String,
    val message: String
) {
    var isSuccess : Boolean
    var timestamp : LocalDateTime

    init {
        isSuccess = status == 200
        timestamp = LocalDateTime.now()
    }
}