package com.jaewoo.srs.common.auth.controller

import com.jaewoo.srs.common.auth.domain.dto.LoginRequest
import com.jaewoo.srs.common.auth.domain.dto.LoginResponse
import com.jaewoo.srs.common.auth.domain.dto.LoginUser
import com.jaewoo.srs.common.auth.domain.dto.Token
import com.jaewoo.srs.common.auth.domain.entity.CacheSession
import com.jaewoo.srs.common.auth.service.CacheSessionService
import com.jaewoo.srs.common.auth.service.LoginService
import com.jaewoo.srs.core.security.jwt.JwtTokenProvider
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Api(
    tags = ["Login"],
    description = "사용자 로그인 API"
)
@RestController
@RequestMapping("/api")
class LoginApiController(
    private val loginService: LoginService,
    private val cacheSessionService: CacheSessionService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @ApiOperation(value = "로그인", notes = "사용자 로그인 인증 처리")
    @PostMapping("/signin")
    fun signin(
        @Valid @RequestBody dto: LoginRequest
    ): LoginResponse {
        // 사용자 조회
        val loginUser = loginService.signin(dto.loginId, dto.password)

        // 로그인사용자 Session 생성
        val session = loginUser.toSessionUser()

        val saveSession = cacheSessionService.saveSession(CacheSession(session))

        val sessionId = saveSession.id!!
        val accessToken = jwtTokenProvider.createAccessToken(sessionId)
        val refreshToken = jwtTokenProvider.createRefreshToken(sessionId)

        return LoginResponse(
            token = Token(
                accessToken = accessToken,
                refreshToken = refreshToken
            ),
            user = LoginUser(
                name = loginUser.name,
                email = loginUser.loginId,
                mobileNo = loginUser.mobileNo,
                id = loginUser.id
            )
        )
    }
}