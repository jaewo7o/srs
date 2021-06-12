package com.jaewoo.srs.common.code.controller

import com.jaewoo.srs.common.code.domain.dto.*
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
    @ApiOperation(value = "코드그룹 검색")
    @GetMapping("/api/group-codes")
    fun searchGroupCodesPageable(dto: SearchGroupCodeRequest, pageable: Pageable)
            = groupCodeService.searchGroupCodesPageable(dto, pageable)

    @ApiOperation(value = "코드그룹 단건 조회")
    @GetMapping("/api/group-codes/{groupCode}")
    fun getGroupCode(@PathVariable groupCode: String)
            = groupCodeService.getGroupCode(groupCode)

    @ApiOperation(value = "코드그룹 단건 수정")
    @PutMapping("/api/group-codes/{groupCode}")
    fun updateGroupCode(@PathVariable groupCode: String, @RequestBody dto: UpdateGroupCodeRequest) =
        groupCodeService.updateGroupCode(groupCode, dto)

    @ApiOperation(value = "코드그룹 신규 생성")
    @PostMapping("/api/group-codes")
    fun createGroupCode(@Valid @RequestBody dto: CreateGroupCodeRequest) {
        groupCodeService.createGroupCode(dto)
    }

    @ApiOperation(value = "코드 단건 조회")
    @GetMapping("/api/group-codes/{groupCode}/codes/{code}")
    fun getCode(@PathVariable groupCode: String, @PathVariable code: String)
        = codeService.getCode(groupCode, code)

    @ApiOperation(value = "코드 단건 수정")
    @PutMapping("/api/group-codes/{groupCode}/codes/{code}")
    fun updateCode(@PathVariable groupCode: String, @PathVariable code: String, @RequestBody dto: UpdateCodeRequest) =
        codeService.updateCode(groupCode, code, dto)

    @ApiOperation(value = "코드 신규 생성")
    @PostMapping("/api/group-codes/{groupCode}/codes")
    fun createCode(@Valid @RequestBody dto: CreateCodeRequest) = codeService.createCode(dto)
}