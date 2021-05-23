package com.jaewoo.srs.common.auth.controller

import com.jaewoo.srs.app.user.domain.dto.CreateUserRequest
import com.jaewoo.srs.app.user.service.UserService
import com.jaewoo.srs.common.auth.domain.dto.LoginRequest
import com.jaewoo.srs.common.auth.domain.dto.LoginResponse
import com.jaewoo.srs.common.auth.domain.dto.LoginUser
import com.jaewoo.srs.common.auth.domain.dto.Token
import com.jaewoo.srs.common.auth.domain.entity.RefreshToken
import com.jaewoo.srs.common.auth.service.AuthService
import com.jaewoo.srs.common.auth.service.TokenService
import com.jaewoo.srs.core.config.jwt.JwtTokenProvider
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class AuthApiController(
    private val userService: UserService,
    private val authService: AuthService,
    private val tokenService: TokenService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/signup")
    fun signup(@Valid @RequestBody dto: CreateUserRequest) = userService.createUser(dto)

    @PostMapping("/signin")
    fun signin(
        @Valid @RequestBody dto: LoginRequest
    ): LoginResponse {
        val loginUser = authService.signin(dto.loginId, dto.password)

        val loginId = loginUser.loginId
        val accessToken = jwtTokenProvider.createAccessToken(loginId)
        val refreshToken = jwtTokenProvider.createRefreshToken(loginId)

        tokenService.saveRefreshToken(RefreshToken(refreshToken, loginUser))

        return LoginResponse(
            token = Token(
                accessToken = accessToken,
                refreshToken = refreshToken
            ),
            user = LoginUser(
                name = loginUser.name,
                email = loginUser.loginId,
                mobileNo = loginUser.mobileNo,
                id = loginUser.id!!
            )
        )
    }

    @PostMapping("/reissueAccessToken")
    fun reissueToken(request: HttpServletRequest): Token {
        var accessToken = ""

        val refreshToken = jwtTokenProvider.resolveRefreshToken(request)
        val user = tokenService.getRefreshToken(refreshToken).user
        if (user.loginId.equals(jwtTokenProvider.getUserPk(refreshToken))) {
            accessToken = jwtTokenProvider.createAccessToken(user.loginId)
        }

        return Token(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}