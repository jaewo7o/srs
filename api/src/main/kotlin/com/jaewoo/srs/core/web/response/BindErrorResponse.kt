package com.jaewoo.srs.core.web.response

class BindErrorResponse(
    status: Int,
    message: String,
    val fields: List<ErrorField>
) : ResponseWrapper(status, "BINDERROR", message)

data class ErrorField(
    val fieldName: String,
    val errorMessage: String,
    val bindValue: Any?
)