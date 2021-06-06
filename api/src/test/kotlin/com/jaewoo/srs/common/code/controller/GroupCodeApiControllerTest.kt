package com.jaewoo.srs.common.code.controller

import com.jaewoo.srs.app.user.buildCreateGroupCodeRequest
import com.jaewoo.srs.common.code.dao.GroupCodeRepository
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import com.jaewoo.srs.core.test.SpringWebTestSupport
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

internal class GroupCodeApiControllerTest(
    private val groupCodeRepository: GroupCodeRepository
) : SpringWebTestSupport() {
    val BASE_URL = "/api/group-codes"

    @Test
    fun `그룹코드 검색 - 페이지`() {
    }

    @Test
    fun `그룹코드 단건 조회`() {
    }

    @Test
    fun `그룹코드 단건 수정`() {
    }

    @Test
    @Transactional
    fun `그룹코드 생성`() {
        // given
        val dto = buildCreateGroupCodeRequest()

        // when
        mockMvc.post(BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
        }.andDo {
            print()
        }

        // then
        val saveGroupCode = groupCodeRepository.findById(dto.groupCode).orElseThrow { SrsDataNotFoundException() }
        Assertions.assertThat(saveGroupCode).isNotNull
        Assertions.assertThat(saveGroupCode.groupCodeNameKo).isEqualTo(dto.groupCodeNameKo)
        Assertions.assertThat(saveGroupCode.groupCodeNameEn).isEqualTo(dto.groupCodeNameEn)
    }
}