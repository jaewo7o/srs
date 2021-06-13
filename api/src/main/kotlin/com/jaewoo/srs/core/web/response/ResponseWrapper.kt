package com.jaewoo.srs.core.web.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

open class ResponseWrapper(
    val status: Int,
    var code: String,
    val message: String
) {
    @JsonProperty("isSuccess")
    private var isSuccess: Boolean
    private var timestamp: LocalDateTime

    init {
        isSuccess = status == 200
        code = when (status) {
            200 -> "OK"
            499 -> "ERR-0001"
            else -> "ERR-9999"
        }

        timestamp = LocalDateTime.now()
    }
}