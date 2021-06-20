package com.jaewoo.srs.common.auth.controller

import com.jaewoo.srs.common.auth.domain.dto.Token
import com.jaewoo.srs.common.auth.service.CacheSessionService
import com.jaewoo.srs.core.exception.SrsEnumRuntimeException
import com.jaewoo.srs.core.exception.code.ErrorCode
import com.jaewoo.srs.core.security.jwt.JwtTokenProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@Api(
    tags = ["Token"],
    description = "인증토큰 재발급 API"
)
@RestController
@RequestMapping("/api")
class TokenApiController(
    private val cacheSessionService: CacheSessionService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @ApiOperation(value = "토큰재발행", notes = "Access Token이 만료된 경우 Refresh Token을 사용해서 Access Token을 재발행한다.")
    @PostMapping("/reissue-token")
    fun reissueToken(request: HttpServletRequest): Token {
        var refreshToken: String
        var sessionId: String
        try {
            refreshToken = jwtTokenProvider.resolveRefreshToken(request)
            sessionId = jwtTokenProvider.getSessionId(refreshToken)
        } catch (e: SignatureException) { //서명 오류 or JWT 구조 문제
            throw SrsEnumRuntimeException(ErrorCode.REFRESHTOKEN_SIGNATURE_INVALID)
        } catch (e: ExpiredJwtException) {//유효 기간이 지난 JWT를 수신한 경우
            throw SrsEnumRuntimeException(ErrorCode.REFRESHTOKEN_EXPIRED)
        } catch (e: Exception) {
            throw SrsEnumRuntimeException(ErrorCode.REFRESHTOKEN_AUTHENTICATION_ERROR)
        }

        // cache에 세션정보 있으면 재갱신하고 없으면 exception 처리
        val currentSession = cacheSessionService.getOptSession(sessionId)
        if (currentSession.isPresent) {
            cacheSessionService.saveSession(currentSession.get())
        } else {
            throw SrsEnumRuntimeException(ErrorCode.REISSUE_TOKEN_SESSION_NOT_EXIST)
        }

        // access token 재발행
        val accessToken = jwtTokenProvider.createAccessToken(sessionId)

        return Token(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}