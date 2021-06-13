package com.jaewoo.srs.core.filter

import com.jaewoo.srs.core.exception.code.ErrorCode
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
                    }
                    false -> {
                    }
                }
            }
        } catch (e: SignatureException) { //서명 오류 or JWT 구조 문제
            request.setAttribute("errorCode", ErrorCode.TOKEN_SIGNATURE_INVALID)
        } catch (e: ExpiredJwtException) {//유효 기간이 지난 JWT를 수신한 경우
            request.setAttribute("errorCode", ErrorCode.TOKEN_EXPIRED)
        } catch (e: Exception) {
            request.setAttribute("errorCode", ErrorCode.TOKEN_AUTHENTICATION_ERROR)
        }

        chain.doFilter(request, response)
    }
}