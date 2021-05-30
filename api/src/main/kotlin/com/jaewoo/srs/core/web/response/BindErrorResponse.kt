package com.jaewoo.srs.core.web.response

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import java.time.LocalDateTime

class BindErrorResponse (
    status: HttpStatus,
    val error: BindErrorDetail
) : ResponseWrapper(status) {

}

data class BindErrorDetail(
    val message: String,
    val fields: List<BindErrorField>
) {
}

data class BindErrorField(
    val fieldName:String,
    val errorMessage: String,
    val bindValue: Any?
)