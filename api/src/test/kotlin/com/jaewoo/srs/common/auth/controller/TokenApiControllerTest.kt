package com.jaewoo.srs.common.auth.controller

import com.jaewoo.srs.SpringWebTestSupport
import com.jaewoo.srs.app.user.buildSessionUser
import com.jaewoo.srs.common.auth.domain.entity.CacheSession
import com.jaewoo.srs.common.auth.service.CacheSessionService
import com.jaewoo.srs.core.security.jwt.JwtTokenProvider
import com.jaewoo.srs.core.security.properties.SecurityProperties
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

internal class TokenApiControllerTest(
    private val cacheSessionService: CacheSessionService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val securityProperties: SecurityProperties
) : SpringWebTestSupport() {

    val baseUrl = "/api/reissue-token"

    @Test
    @Transactional
    fun `Access Token 재발행 성공 케이스`() {
        // given
        // 로그인사용자 Session 생성
        val saveSession = cacheSessionService.saveSession(CacheSession(buildSessionUser()))

        val sessionId = saveSession.id!!
        val accessToken = jwtTokenProvider.createAccessToken(sessionId)
        val refreshToken = jwtTokenProvider.createRefreshToken(sessionId)

        // when & then
        mockMvc.post(baseUrl) {
            header(securityProperties.headerStringRefreshToken, refreshToken)
        }.andExpect {
            status { isOk() }
            jsonPath("$..refreshToken") { value(refreshToken) }
            jsonPath("$..accessToken") { !equals(accessToken) }
        }.andDo {
            print()
        }
    }

    @Test
    @Transactional
    fun `Access Token 재발행 실패 케이스`() {
        // given
        // 로그인사용자 Session 생성
        val saveSession = cacheSessionService.saveSession(CacheSession(buildSessionUser()))

        val sessionId = saveSession.id!!
        val refreshToken = jwtTokenProvider.createRefreshToken(sessionId)

        // when & then
        mockMvc.post(baseUrl) {
            header(securityProperties.headerStringRefreshToken, refreshToken + "1")
        }.andExpect {
            status { isUnauthorized() }
        }.andDo {
            print()
        }
    }
}