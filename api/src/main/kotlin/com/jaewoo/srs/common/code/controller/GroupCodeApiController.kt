package com.jaewoo.srs.common.code.controller

import com.jaewoo.srs.common.code.domain.dto.CreateGroupCodeRequest
import com.jaewoo.srs.common.code.domain.dto.SearchGroupCodeRequest
import com.jaewoo.srs.common.code.domain.dto.UpdateGroupCodeRequest
import com.jaewoo.srs.common.code.service.GroupCodeService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(
    tags = ["Group Code"],
    description = "공통코드그룹"
)
@RequestMapping("/api/group-codes")
@RestController
class GroupCodeApiController(
    private val groupCodeService: GroupCodeService
) {
    @ApiOperation(value = "코드그룹 검색")
    @GetMapping("")
    fun searchGroupCodesPageable(dto: SearchGroupCodeRequest, pageable: Pageable)
        = groupCodeService.searchGroupCodesPageable(dto, pageable)

    @ApiOperation(value = "코드그룹 단건 조회")
    @GetMapping("/{groupCode}")
    fun getGroupCode(@PathVariable groupCode: String)
        = groupCodeService.getGroupCode(groupCode)

    @ApiOperation(value = "코드그룹 단건 수정")
    @PutMapping("/{groupCode}")
    fun updateGroupCode(@PathVariable groupCode: String, @RequestBody dto: UpdateGroupCodeRequest) =
        groupCodeService.updateGroupCode(groupCode, dto)

    @ApiOperation(value = "코드그룹 신규 생성")
    @PostMapping("")
    fun createGroupCode(@Valid @RequestBody dto: CreateGroupCodeRequest) {
        groupCodeService.createGroupCode(dto)
    }
}