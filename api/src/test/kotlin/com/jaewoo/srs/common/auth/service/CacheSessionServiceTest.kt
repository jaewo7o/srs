package com.jaewoo.srs.common.auth.service

import com.jaewoo.srs.app.user.buildUser
import com.jaewoo.srs.common.auth.domain.entity.CacheSession
import com.jaewoo.srs.core.test.SpringTestSupport
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional

internal class CacheSessionServiceTest(
    var cacheSessionService: CacheSessionService
) : SpringTestSupport() {

    @Test
    @Transactional
    fun `레디스 토큰 저장 조회 테스트`() {
        // given
        val loginUser = buildUser()

        // 로그인사용자 Session 생성
        val session = loginUser.toSessionUser()

        // when
        val saveSession = cacheSessionService.saveSession(CacheSession(session))

        // then
        val retrieveSession = cacheSessionService.getSession(saveSession.id!!)
        Assertions.assertThat(retrieveSession).isNotNull
        Assertions.assertThat(retrieveSession.sessionUser.loginId).isEqualTo(loginUser.loginId)
    }
}