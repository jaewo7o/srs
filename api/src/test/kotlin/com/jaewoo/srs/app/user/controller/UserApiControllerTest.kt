package com.jaewoo.srs.app.user.controller

import com.jaewoo.srs.app.user.buildCreateUserRequest
import com.jaewoo.srs.app.user.buildUser
import com.jaewoo.srs.app.user.domain.dto.UpdateUserRequest
import com.jaewoo.srs.app.user.domain.entity.QUser
import com.jaewoo.srs.core.test.SpringWebTestSupport
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
internal class UserApiControllerTest : SpringWebTestSupport() {

    private val baseUrl = "/api/users"

    @Transactional
    @Test
    internal fun `사용자 목록조회(paging)`() {
        // given
        (1..10).map {
            buildUser()
        }.apply {
            saveAll(this)
        }

        val pageSize = 5
        val pageNumber = 0

        // when & then
        mockMvc.get(baseUrl) {
            param("size", pageSize.toString())
            param("page", pageNumber.toString())
        }.andExpect {
            status { isOk() }
            jsonPath("$..size") { value(pageSize) }
            jsonPath("$..number") { value(pageNumber) }
        }
    }

    @Transactional
    @Test
    internal fun `PK로 사용자 단건 조회`() {
        // given
        val user = save(buildUser())

        // when & then
        mockMvc
            .get("$baseUrl/${user.id}") {
                contentType = MediaType.APPLICATION_JSON
            }
            .andDo {
                print()
            }
            .andExpect {
                status { isOk() }
                //jsonPath("$[0].loginId") { value(user.loginId) }
            }
    }

    @Transactional
    @Test
    internal fun `사용자 단건 생성`() {
        // given
        val dto = buildCreateUserRequest()

        // when & then
        mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andDo {
            print()
        }.andExpect {
            status { isOk() }
        }
    }

    @Transactional
    @Test
    internal fun `사용자 단건 생성 - loginid 중복생성 에러`() {
        // given
        val dto = buildCreateUserRequest()
        save(dto.toEntity())

        // when & then
        mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andDo {
            print()
        }.andExpect {
            status { is4xxClientError() }
        }
    }

    @Transactional
    @Test
    internal fun `사용자 단건 생성 - 요청 파라미터 Validation 에러`() {
        // given
        val dto = buildCreateUserRequest(name = "")

        // when & then
        mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andDo {
            print()
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Transactional
    @Test
    internal fun `사용자 단건 수정`() {
        // given
        val userId = save(buildUser(name = "oldName")).id!!

        val dto = UpdateUserRequest(
            name = "newName",
            mobileNo = "newMobileNo"
        )

        // when
        mockMvc.put(
            "$baseUrl/$userId"
        ) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andDo {
            print()
        }.andExpect {
            status { isOk() }
        }

        // then
        val user = QUser.user
        val resultUser = query.selectFrom(user)
            .where(user.id.eq(userId))
            .fetchOne()!!

        then(resultUser.name).isEqualTo(dto.name)
        then(resultUser.mobileNo).isEqualTo(dto.mobileNo)
    }
}