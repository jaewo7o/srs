package com.jaewoo.srs.core.web.response

import org.springframework.http.HttpStatus

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