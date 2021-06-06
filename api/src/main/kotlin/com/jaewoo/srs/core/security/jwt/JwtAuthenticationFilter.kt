package com.jaewoo.srs.core.security.jwt

import com.jaewoo.srs.core.context.SrsContext
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
            response.sendError(401, "SignatureException error")
        } catch (e: ExpiredJwtException) {//유효 기간이 지난 JWT를 수신한 경우
            response.sendError(401, "ExpiredJwtException error")
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
        }

        chain.doFilter(request, response)
    }
}