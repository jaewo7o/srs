package com.jaewoo.srs.core.web.handler

import com.jaewoo.srs.core.web.response.ResponseWrapper
import com.jaewoo.srs.core.web.response.SrsResponse
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice
class GlobalResponseHandler : ResponseBodyAdvice<Any?> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any {
        if (body == null) {
            return ""
        }

        if (!selectedContentType.includes(MediaType.APPLICATION_JSON)) {
            return body
        }

        if (body is ResponseWrapper) {
            return ResponseEntity(body, body.status)
        } else {
            return ResponseEntity.ok(SrsResponse(HttpStatus.OK, body))
        }
    }
}