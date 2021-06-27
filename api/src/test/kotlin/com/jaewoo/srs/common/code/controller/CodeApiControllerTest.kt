package com.jaewoo.srs.common.code.controller

import com.jaewoo.srs.SpringWebTestSupport
import com.jaewoo.srs.app.user.buildCode
import com.jaewoo.srs.app.user.buildCreateCodeRequest
import com.jaewoo.srs.app.user.buildCreateGroupCodeRequest
import com.jaewoo.srs.app.user.buildGroupCode
import com.jaewoo.srs.common.code.dao.CodeRepository
import com.jaewoo.srs.common.code.dao.GroupCodeRepository
import com.jaewoo.srs.common.code.domain.dto.UpdateCodeRequest
import com.jaewoo.srs.common.code.domain.dto.UpdateGroupCodeRequest
import com.jaewoo.srs.common.code.domain.entity.CodeKey
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.transaction.annotation.Transactional

internal class CodeApiControllerTest(
    private val groupCodeRepository: GroupCodeRepository,
    private val codeRepository: CodeRepository
) : SpringWebTestSupport() {

    val baseUrl = "/api/group-codes"

    @Test
    @Transactional
    fun `그룹코드 검색 - 페이지`() {
        // given
        val searchName = "NAMEKO5"

        (1..10).map {
            buildGroupCode(
                groupCode = "CODE${it}",
                groupCodeNameKo = "NAMEKO${it}",
                groupCodeNameEn = "NAMEEN${it}"
            )
        }.also {
            saveAll(it)
        }

        val pageSize = 5
        val pageNumber = 0

        // when & then
        mockMvc.get(baseUrl) {
            param("page", pageNumber.toString())
            param("size", pageSize.toString())
            param("name", searchName)
        }.andExpect {
            status { isOk() }
            jsonPath("$..size") { value(pageSize) }
            jsonPath("$..number") { value(pageNumber) }
            jsonPath("$..numberOfElements") { value(1) }
        }.andDo { print() }
    }

    @Test
    @Transactional
    fun `그룹코드 단건 조회`() {
        // given
        val saveGroupCode = save(buildGroupCode())

        // when & then
        mockMvc.get("${baseUrl}/${saveGroupCode.groupCode}")
            .andExpect {
                status { isOk() }
                jsonPath("$..groupCode") { value(saveGroupCode.groupCode) }
                jsonPath("$..groupCodeNameKo") { value(saveGroupCode.groupCodeNameKo) }
                jsonPath("$..groupCodeNameEn") { value(saveGroupCode.groupCodeNameEn) }
            }.andDo { print() }
    }

    @Test
    @Transactional
    fun `그룹코드 단건 수정`() {
        // given
        val beforeGroupCode = save(buildGroupCode())
        val dto = UpdateGroupCodeRequest(
            groupCodeNameKo = beforeGroupCode.groupCodeNameKo + "-수정",
            groupCodeNameEn = beforeGroupCode.groupCodeNameEn + "-수정"
        )

        // when
        mockMvc.put("${baseUrl}/${beforeGroupCode.groupCode}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
        }.andDo { print() }

        // then
        val saveGroupCode =
            groupCodeRepository.findById(beforeGroupCode.groupCode).orElseThrow { SrsDataNotFoundException() }
        Assertions.assertThat(saveGroupCode).isNotNull
        Assertions.assertThat(saveGroupCode.groupCodeNameKo).isEqualTo(dto.groupCodeNameKo)
        Assertions.assertThat(saveGroupCode.groupCodeNameEn).isEqualTo(dto.groupCodeNameEn)
    }

    @Test
    @Transactional
    fun `그룹코드 생성`() {
        // given
        val dto = buildCreateGroupCodeRequest()

        // when
        mockMvc.post(baseUrl) {
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

    @Test
    @Transactional
    fun `코드 목록 조회`() {
        // given
        val saveGroupCode = save(buildGroupCode())

        (1..10).map {
            buildCode(
                groupCode = saveGroupCode.groupCode,
                code = "A$it",
                codeNameKo = "contents$it",
                codeNameEn = "contents$it"
            )
        }.also {
            saveAll(it)
        }

        // when & then
        mockMvc.get("${baseUrl}/${saveGroupCode.groupCode}/codes")
            .andExpect {
                status { isOk() }
                jsonPath("$.body.data[0].groupCode") { value(saveGroupCode.groupCode) }
                jsonPath("$.body.data.length()") { value(10) }
            }.andDo {
                print()
            }
    }

    @Test
    @Transactional
    fun `코드 단건 조회`() {
        // given
        val saveGroupCode = save(buildGroupCode())
        val saveCode = save(
            buildCode(
                groupCode = saveGroupCode.groupCode
            )
        )

        // when & then
        mockMvc.get("${baseUrl}/${saveCode.groupCode}/codes/${saveCode.code}")
            .andExpect {
                status { isOk() }
                jsonPath("$.body.data.groupCode") { value(saveCode.groupCode) }
                jsonPath("$.body.data.code") { value(saveCode.code) }
            }.andDo {
                print()
            }
    }

    @Test
    @Transactional
    fun `코드 단건 생성`() {
        // given
        val saveGroupCode = save(buildGroupCode())
        val dto = buildCreateCodeRequest()

        // when
        val groupCode = saveGroupCode.groupCode
        mockMvc.post("${baseUrl}/$groupCode/codes") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
        }.andDo {
            print()
        }

        // then
        val findCode = codeRepository.getOne(CodeKey(groupCode, dto.code))
        Assertions.assertThat(findCode.groupCode).isEqualTo(groupCode)
        Assertions.assertThat(findCode.code).isEqualTo(dto.code)
        Assertions.assertThat(findCode.codeNameKo).isEqualTo(dto.codeNameKo)
        Assertions.assertThat(findCode.codeNameEn).isEqualTo(dto.codeNameEn)
        Assertions.assertThat(findCode.sortRank).isEqualTo(dto.sortRank)
    }

    @Test
    @Transactional
    fun `코드 단건 수정`() {
        // given
        val saveGroupCode = save(buildGroupCode())
        val saveCode = save(
            buildCode(
                groupCode = saveGroupCode.groupCode
            )
        )

        val dto = UpdateCodeRequest(
            codeNameKo = saveCode.codeNameKo + "-수정",
            codeNameEn = saveCode.codeNameEn + "-수정",
            sortRank = 1
        )

        // when
        mockMvc.put("${baseUrl}/${saveCode.groupCode}/codes/${saveCode.code}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
        }.andDo {
            print()
        }

        // then
        val findCode = codeRepository.getOne(CodeKey(saveCode.groupCode, saveCode.code))
        Assertions.assertThat(findCode.groupCode).isEqualTo(saveCode.groupCode)
        Assertions.assertThat(findCode.code).isEqualTo(saveCode.code)
        Assertions.assertThat(findCode.codeNameKo).isEqualTo(dto.codeNameKo)
        Assertions.assertThat(findCode.codeNameEn).isEqualTo(dto.codeNameEn)
        Assertions.assertThat(findCode.sortRank).isEqualTo(dto.sortRank)
    }
}