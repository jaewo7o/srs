package com.jaewoo.srs.common.auth.service

import com.jaewoo.srs.app.user.buildUser
import com.jaewoo.srs.common.auth.domain.entity.RefreshToken
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class TokenServiceTest {

    @Autowired
    private lateinit var tokenService: TokenService

    @Test
    @Transactional
    fun `레디스 토큰 저장 조회 테스트`() {
        // given
        val refreshToken = "TOKEN1"
        val loginUser = buildUser()

        // when
        tokenService.saveRefreshToken(RefreshToken(refreshToken, loginUser))

        // then
        val tokenObject = tokenService.getRefreshToken(refreshToken)
        Assertions.assertThat(tokenObject).isNotNull
        Assertions.assertThat(tokenObject.refreshToken).isEqualTo(refreshToken)
        Assertions.assertThat(tokenObject.user.id).isEqualTo(loginUser.id)
        Assertions.assertThat(tokenObject.user.loginId).isEqualTo(loginUser.loginId)
    }
}