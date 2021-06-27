package com.jaewoo.srs.core.auth.controller

import com.jaewoo.srs.SpringWebTestSupport
import com.jaewoo.srs.app.user.buildCreateUserRequest
import com.jaewoo.srs.common.auth.domain.dto.LoginRequest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional


internal class LoginApiControllerTest(
    private val encodePassword: BCryptPasswordEncoder
) : SpringWebTestSupport() {

    private val baseUrl = "/api/signin"

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
        mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(LoginRequest(dto.loginId, dto.password))
        }.andExpect {
            status { isOk() }
            jsonPath("$..['accessToken']") {
                isNotEmpty()
            }
            jsonPath("$..['refreshToken']") {
                isNotEmpty()
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
        mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(LoginRequest(dto.loginId, dto.password + "x"))
        }.andExpect {
            status { is4xxClientError() }
        }.andDo { print() }
    }
}