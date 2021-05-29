package com.jaewoo.srs.core.web.response

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import java.time.LocalDateTime

class BindErrorResponse {
    val isSuccess = false
    lateinit var error: BindErrorDetail
}

data class BindErrorDetail(
    val status: HttpStatus,
    val message: String,
    val fields: List<BindErrorField>
) {
    val code: Int
    val name: String
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    val timestamp: LocalDateTime

    init {
        code = status.value()
        name = status.name
        timestamp = LocalDateTime.now()
    }
}

data class BindErrorField(
    val fieldName:String,
    val errorMessage: String,
    val bindValue: Any?
)