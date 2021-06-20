package com.jaewoo.srs.core.web.handler

import com.jaewoo.srs.common.message.service.MessageResolveService
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import com.jaewoo.srs.core.exception.SrsEnumRuntimeException
import com.jaewoo.srs.core.exception.SrsRuntimeException
import com.jaewoo.srs.core.logging.Log
import com.jaewoo.srs.core.web.response.BindErrorResponse
import com.jaewoo.srs.core.web.response.ErrorField
import com.jaewoo.srs.core.web.response.ErrorResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import java.io.PrintWriter
import java.io.StringWriter
import javax.persistence.EntityNotFoundException
import javax.persistence.NoResultException

@RestControllerAdvice
class GlobalExceptionHandler(
    @Value("\${spring.profiles.active}") private val activeProfile: String,
    private val messageResolveService: MessageResolveService
) {
    companion object : Log

    @ExceptionHandler(value = [BindException::class])
    fun handleBindException(ex: BindException): ResponseEntity<BindErrorResponse> {
        return buildBindErrorResponse(HttpStatus.BAD_REQUEST, "Request Binding Exception", ex.bindingResult)
    }

    @ExceptionHandler(
        HttpClientErrorException.BadRequest::class,
        MissingServletRequestParameterException::class,
        IllegalArgumentException::class
    )
    fun constraintViolationException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Bad request error", e)
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad request", e)
    }

    @ExceptionHandler(
        EntityNotFoundException::class,
        NoSuchElementException::class,
        NoResultException::class,
        EmptyResultDataAccessException::class,
        IndexOutOfBoundsException::class,
        KotlinNullPointerException::class
    )
    fun handleNotFoundException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Resource not found error", e)
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Resource not found", e)
    }

    @ExceptionHandler(value = [SrsDataNotFoundException::class])
    fun handleSrsDataNotFoundException(ex: Exception): ResponseEntity<*> {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Data not found exception", ex)
    }

    @ExceptionHandler(value = [SrsRuntimeException::class])
    fun handleSrsRuntimeException(ex: SrsRuntimeException): ResponseEntity<*> {
        val exceptionMessage = messageResolveService.resolveMessage(ex.key)
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, exceptionMessage, ex)
    }

    @ExceptionHandler(value = [SrsEnumRuntimeException::class])
    fun handleSrsEnumRuntimeException(ex: SrsEnumRuntimeException): ResponseEntity<*> {
        val errorCode = ex.errorCode
        return buildErrorResponse(HttpStatus.resolve(errorCode.status)!!, errorCode.message, ex)
    }

    @ExceptionHandler(
        Exception::class
    )
    fun handleInternalServerError(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("internal server error", e)
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Generic internal error", e)
    }

    private fun buildBindErrorResponse(
        status: HttpStatus,
        message: String,
        bindResult: BindingResult
    ): ResponseEntity<BindErrorResponse> {

        val fieldErrors = bindResult.allErrors
            .map {
                ErrorField(
                    fieldName = (it as FieldError).field,
                    errorMessage = it.defaultMessage!!,
                    bindValue = it.rejectedValue
                )
            }.toList()

        val errorResponse = BindErrorResponse(status.value(), message, fieldErrors)
        return ResponseEntity(errorResponse, status)
    }

    private fun buildErrorResponse(
        status: HttpStatus,
        message: String,
        e: Exception
    ): ResponseEntity<ErrorResponse> {
        // converting the exception stack trace to a string
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        val stackTrace = sw.toString()

        // profile base stack trace message include or not logic
        val stackTraceMessage =
            when (activeProfile) {
                "prod" -> null // returning no stack trace
                else -> stackTrace // default behavior
            }

        val errorResponse = ErrorResponse(status.value(), message, stackTraceMessage)
        return ResponseEntity(errorResponse, status)
    }
}