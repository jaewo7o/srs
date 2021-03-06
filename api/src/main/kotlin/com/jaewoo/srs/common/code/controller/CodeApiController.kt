package com.jaewoo.srs.common.code.controller

import com.jaewoo.srs.common.code.domain.dto.*
import com.jaewoo.srs.common.code.domain.entity.Code
import com.jaewoo.srs.common.code.service.CodeService
import com.jaewoo.srs.common.code.service.GroupCodeService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(
    tags = ["Code"],
    description = "공통코드"
)
@RestController
class CodeApiController(
    private val groupCodeService: GroupCodeService,
    private val codeService: CodeService
) {

    companion object {
        const val baseUrl = "/api/group-codes"
        const val aBaseUrl = "/api/anonymous/group-codes"
    }

    @ApiOperation(value = "코드그룹 검색")
    @GetMapping(value = [aBaseUrl, baseUrl])
    fun searchGroupCodesPageable(dto: SearchGroupCodeRequest, pageable: Pageable) =
        groupCodeService.searchGroupCodesPageable(dto, pageable)

    @ApiOperation(value = "코드그룹 단건 조회")
    @GetMapping(value = ["$aBaseUrl/{groupCode}", "$baseUrl/{groupCode}"])
    fun getGroupCode(@PathVariable groupCode: String) = groupCodeService.getGroupCode(groupCode)

    @ApiOperation(value = "코드그룹 단건 수정")
    @PutMapping(value = ["$aBaseUrl/{groupCode}", "$baseUrl/{groupCode}"])
    fun updateGroupCode(@PathVariable groupCode: String, @RequestBody dto: UpdateGroupCodeRequest) =
        groupCodeService.updateGroupCode(groupCode, dto)

    @ApiOperation(value = "코드그룹 신규 생성")
    @PostMapping(value = [aBaseUrl, baseUrl])
    fun createGroupCode(@Valid @RequestBody dto: CreateGroupCodeRequest) {
        groupCodeService.createGroupCode(dto)
    }

    @ApiOperation(value = "코드그룹 삭제")
    @DeleteMapping(value = ["$aBaseUrl/{groupCode}", "$baseUrl/{groupCode}"])
    fun deleteGroupCode(@PathVariable groupCode: String) {
        groupCodeService.deleteGroupCode(groupCode)
    }

    @ApiOperation(value = "코드 목록 조회")
    @GetMapping(value = ["$aBaseUrl/{groupCode}/codes", "$baseUrl/{groupCode}/codes"])
    fun getCode(@PathVariable groupCode: String) = codeService.getCodes(groupCode).map {
        SearchCodeResponse(
            groupCode = it.groupCode,
            code = it.code,
            codeNameKo = it.codeNameKo,
            codeNameEn = it.codeNameEn,
            sortRank = it.sortRank
        )
    }

    @ApiOperation(value = "코드 단건 조회")
    @GetMapping(value = ["$aBaseUrl/{groupCode}/codes/{code}", "$baseUrl/{groupCode}/codes/{code}"])
    fun getCode(@PathVariable groupCode: String, @PathVariable code: String) = codeService.getCode(groupCode, code)

    @ApiOperation(value = "코드 단건 수정")
    @PutMapping(value = ["$aBaseUrl/{groupCode}/codes/{code}", "$baseUrl/{groupCode}/codes/{code}"])
    fun updateCode(@PathVariable groupCode: String, @PathVariable code: String, @RequestBody dto: UpdateCodeRequest) =
        codeService.updateCode(groupCode, code, dto)

    @ApiOperation(value = "코드 신규 생성")
    @PostMapping(value = ["$aBaseUrl/{groupCode}/codes", "$baseUrl/{groupCode}/codes"])
    fun createCode(
        @PathVariable groupCode: String,
        @Valid @RequestBody dto: CreateCodeRequest
    ): Code {
        dto.groupCode = groupCode
        return codeService.createCode(dto)
    }
}