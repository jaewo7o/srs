package com.jaewoo.srs.core.security.jwt

import com.jaewoo.srs.core.security.properties.SecurityProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val securityProperties: SecurityProperties
) {
    val keyBytes = securityProperties.secretKey.toByteArray()

    fun createAccessToken(sessionId: String) = generateToken(sessionId, securityProperties.accessTokenExpirationMiniute)

    fun createRefreshToken(sessionId: String) =
        generateToken(sessionId, securityProperties.refreshTokenExpirationMiniute)

    internal fun generateToken(loginId: String, expiredMinute: Long): String {
        val claims = Jwts.claims().setSubject(loginId)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiredMinute))
            .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
            .compact()
    }

    fun resolveAccessToken(req: HttpServletRequest): String {
        return req.getHeader(securityProperties.headerStringAccessToken) ?: ""
    }

    fun resolveRefreshToken(req: HttpServletRequest) = req.getHeader(securityProperties.headerStringRefreshToken) ?: ""

    // token 유효성 검사 + 만료일자 확인
    fun validateToken(token: String): Boolean {
        val claims = Jwts.parser().setSigningKey(keyBytes).parseClaimsJws(token)
        return !claims.body.expiration.before(Date())
    }

    fun getSessionId(token: String): String {
        return Jwts.parser().setSigningKey(keyBytes)
            .parseClaimsJws(token).body.subject
    }
}