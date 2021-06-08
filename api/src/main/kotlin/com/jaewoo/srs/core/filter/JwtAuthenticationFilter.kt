package com.jaewoo.srs.core.filter

import com.jaewoo.srs.core.security.jwt.JwtTokenProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            jwtTokenProvider.resolveAccessToken(request).let {
                when (it.isNotBlank() && jwtTokenProvider.validateToken(it)) {
                    true -> {
                        val loginId = jwtTokenProvider.getUserPk(it)
                        val authentication = jwtTokenProvider.getAuthentication(loginId)
                        SecurityContextHolder.getContext().authentication = authentication

                        //SrsContext.setUser(authentication.principal)
                    }
                    false -> {}
                }
            }
        } catch (e: SignatureException) { //서명 오류 or JWT 구조 문제
            //response.sendError(401, "SignatureException error")
            throw e
        } catch (e: ExpiredJwtException) {//유효 기간이 지난 JWT를 수신한 경우
//            response.sendError(401, "ExpiredJwtException error")
            throw e
//            val errorStatus = HttpStatus.UNAUTHORIZED
//
//            response.status = errorStatus.value()
//            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//
//            val mapper = ObjectMapper()
//            val errorResponse = ErrorResponse(errorStatus, ErrorDetail("Security Error : ${errorStatus.name}", null))
//            response.writer.write(mapper.writeValueAsString(errorResponse))
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
        }

//        val accessToken = jwtTokenProvider.resolveAccessToken(request)
//        if (accessToken.isNotBlank() && jwtTokenProvider.validateToken(accessToken)) {
//            val loginId = jwtTokenProvider.getUserPk(accessToken)
//            val authentication = jwtTokenProvider.getAuthentication(loginId)
//            SecurityContextHolder.getContext().authentication = authentication
//            chain.doFilter(request, response)
//        }

        chain.doFilter(request, response)
    }
}