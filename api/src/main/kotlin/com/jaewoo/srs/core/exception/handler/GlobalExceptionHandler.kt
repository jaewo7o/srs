package com.jaewoo.srs.core.exception.handler

import com.jaewoo.srs.common.message.service.MessageService
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import com.jaewoo.srs.core.exception.SrsRuntimeException
import com.jaewoo.srs.core.logging.Log
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.HttpClientErrorException
import java.io.PrintWriter
import java.io.StringWriter
import javax.persistence.EntityNotFoundException
import javax.persistence.NoResultException

@ControllerAdvice
class GlobalExceptionHandler(
    @Value("\${spring.profiles.active}") private val activeProfile: String,
    private val messageService: MessageService
) {
    companion object : Log

    @ExceptionHandler(value = [BindException::class, MethodArgumentNotValidException::class])
    @ResponseBody
    fun handleMethodArgumentNotValidException(ex: Exception): ResponseEntity<*> {

        var errorMessage: String? = null
        if (ex is BindException) {
            errorMessage = ex.bindingResult.allErrors[0].defaultMessage
        } else if (ex is MethodArgumentNotValidException) {
            errorMessage = ex.bindingResult.allErrors[0].defaultMessage
        }

        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [SrsRuntimeException::class])
    @ResponseBody
    fun handleSrsRuntimeException(ex: SrsRuntimeException): ResponseEntity<*> {
        val exceptionMessage = messageService.resolveMessage(ex.key)
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, exceptionMessage, ex)
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

//    @ExceptionHandler(AuthorizationException::class)
//    fun unauthorizedException(e: Exception): ResponseEntity<ErrorResponse> {
//        return generateErrorResponse(HttpStatus.FORBIDDEN, "You are not authorized to do this operation", e)
//    }
//
//    @ExceptionHandler(AuthenticationException::class)
//    fun forbiddenException(e: Exception): ResponseEntity<ErrorResponse> {
//        return generateErrorResponse(HttpStatus.UNAUTHORIZED, "You are not allowed to do this operation", e)
//    }

    @ExceptionHandler(
        EntityNotFoundException::class,
        NoSuchElementException::class,
        NoResultException::class,
        EmptyResultDataAccessException::class,
        IndexOutOfBoundsException::class,
        KotlinNullPointerException::class
    )
    fun notFoundException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Resource not found error", e)
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Resource not found", e)
    }

    @ExceptionHandler(value = [SrsDataNotFoundException::class])
    @ResponseBody
    fun handleSrsDataNotFoundException(ex: Exception): ResponseEntity<*> {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Data not found exception", ex)
    }

    @ExceptionHandler(
        Exception::class
    )
    fun internalServerErrorException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("internal server error", e)
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Generic internal error", e)
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

        // example: logging the stack trace
        // log.debug(stackTrace)

        // profile base stack trace message include or not logic
        val stackTraceMessage =
            when (activeProfile) {
                "prod" -> null // returning no stack trace
                else -> stackTrace // default behavior
            }

        return ResponseEntity(ErrorResponse(status, message, stackTraceMessage), status)
    }
}