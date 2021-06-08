package com.jaewoo.srs.core.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.jaewoo.srs.core.web.response.ErrorDetail
import com.jaewoo.srs.core.web.response.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        val errorStatus = HttpStatus.UNAUTHORIZED

        response.status = errorStatus.value()
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        val mapper = ObjectMapper()
        val errorResponse = ErrorResponse(errorStatus, ErrorDetail("Security Error : ${errorStatus.name}", null))
        response.writer.write(mapper.writeValueAsString(errorResponse))
    }
}