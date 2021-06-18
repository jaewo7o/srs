package com.jaewoo.srs.common.auth.controller

import com.jaewoo.srs.common.auth.domain.dto.Token
import com.jaewoo.srs.common.auth.service.CacheSessionService
import com.jaewoo.srs.core.exception.SrsEnumRuntimeException
import com.jaewoo.srs.core.exception.code.ErrorCode
import com.jaewoo.srs.core.security.jwt.JwtTokenProvider
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
        val refreshToken = jwtTokenProvider.resolveRefreshToken(request)
        val sessionId = jwtTokenProvider.getSessionId(refreshToken)

        val existSession = cacheSessionService.isExistSession(sessionId)
        if (!existSession) {
            throw SrsEnumRuntimeException(ErrorCode.REISSUE_TOKEN_ERROR)
        }

        val accessToken = jwtTokenProvider.createAccessToken(sessionId)

        return Token(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}