package com.jaewoo.srs.core.auth.controller

import com.jaewoo.srs.app.user.buildCreateUserRequest
import com.jaewoo.srs.common.auth.domain.dto.LoginRequest
import com.jaewoo.srs.core.test.SpringWebTestSupport
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

internal class AuthApiControllerTest(
    private val encodePassword: BCryptPasswordEncoder
) : SpringWebTestSupport() {

    @Transactional
    @Test
    fun `ID,PW 로그인 성공 테스트`() {
        // given
        val dto = buildCreateUserRequest()

        // 로그인 사용자 미리 생성
        save(dto.toEntity().also {
            it.password = encodePassword.encode(it.password)
        })

        // when & then
        mockMvc.post("/api/signin") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(LoginRequest(dto.loginId, dto.password))
        }.andExpect {
            status { isOk() }
//            cookie {
//                exists("accessToken")
//                exists("refreshToken")
//            }
            jsonPath("$.token.accessToken") {
                isNotEmpty()
            }
            jsonPath("$.token.refreshToken") {
                isNotEmpty()
            }
            jsonPath("$.user.name") {
                value(dto.name)
            }

        }.andDo { print() }
    }

    @Transactional
    @Test
    fun `ID,PW 로그인 실패 테스트`() {
        // given
        val dto = buildCreateUserRequest()

        // 로그인 사용자 미리 생성
        save(dto.toEntity().also {
            it.password = encodePassword.encode(it.password)
        })

        // when & then
        mockMvc.post("/api/signin") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(LoginRequest(dto.loginId, dto.password + "x"))
        }.andExpect {
            status { is4xxClientError() }
        }.andDo { print() }
    }
}