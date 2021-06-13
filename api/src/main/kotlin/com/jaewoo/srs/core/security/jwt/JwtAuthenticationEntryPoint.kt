package com.jaewoo.srs.core.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.jaewoo.srs.core.exception.code.ErrorCode
import com.jaewoo.srs.core.web.response.EnumErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint(
    private val mapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorCode: ErrorCode? = request.getAttribute("errorCode") as ErrorCode?
        setErrorResponse(response, errorCode ?: ErrorCode.TOKEN_AUTHENTICATION_ERROR)
    }

    fun setErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        response.status = errorCode.status
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        val errorResponse = EnumErrorResponse(errorCode, null)
        val responseWriter = response.writer
        responseWriter.print(mapper.writeValueAsString(errorResponse))
        responseWriter.flush()
    }
}