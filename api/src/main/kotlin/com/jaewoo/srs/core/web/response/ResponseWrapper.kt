package com.jaewoo.srs.core.web.response

import java.time.LocalDateTime

open class ResponseWrapper(
    val status: Int,
    var code: String,
    val message: String
) {
    private var isSuccess: Boolean
    private var timestamp: LocalDateTime

    init {
        isSuccess = status == 200
        if (code.isBlank() && status == 499) {
            code = "BIZERROR"
        } else {
            code = "ERROR"
        }

        timestamp = LocalDateTime.now()
    }
}