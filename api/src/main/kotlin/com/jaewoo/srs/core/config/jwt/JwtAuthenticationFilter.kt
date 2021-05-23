package com.jaewoo.srs.core.config.jwt

import com.jaewoo.srs.core.config.properties.SecurityProperties
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val securityProperties: SecurityProperties
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            val accessToken = jwtTokenProvider.resolveAccessToken(request)
            if (!securityProperties.securitySkipUrls.contains(request.requestURI)
                && accessToken.isNotBlank()
                && jwtTokenProvider.validateToken(accessToken)
            ) {
                val loginId = jwtTokenProvider.getUserPk(accessToken)
                val authentication = jwtTokenProvider.getAuthentication(loginId)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: SignatureException) { //서명 오류 or JWT 구조 문제
            response.sendError(401, "SignatureException error");
        } catch (e: ExpiredJwtException) {//유효 기간이 지난 JWT를 수신한 경우
            response.sendError(401, "ExpiredJwtException error");
        } catch (e: Exception) {
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response)
    }
}