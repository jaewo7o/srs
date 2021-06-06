package com.jaewoo.srs.common.code.controller

import com.jaewoo.srs.common.code.domain.dto.CreateCommonGroupCodeRequest
import com.jaewoo.srs.common.code.domain.dto.SearchCommonGroupCodeRequest
import com.jaewoo.srs.common.code.domain.dto.UpdateCommonGroupCodeRequest
import com.jaewoo.srs.common.code.service.CommonGroupCodeService
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
class CommonGroupCodeApiController(
    private val commonGroupCodeService: CommonGroupCodeService
) {
    @ApiOperation(value = "코드그룹 검색")
    @GetMapping("/")
    fun searchCommonGroupCodesPageable(dto: SearchCommonGroupCodeRequest, pageable: Pageable)
        = commonGroupCodeService.searchCommonGroupCodesPageable(dto, pageable)

    @ApiOperation(value = "코드그룹 단건 조회")
    @GetMapping("/{groupCode}")
    fun getCommonGroupCode(@PathVariable groupCode: String)
        = commonGroupCodeService.getCommonGroupCode(groupCode)

    @ApiOperation(value = "코드그룹 단건 수정")
    @PutMapping("/{groupCode}")
    fun updateCommonGroupCode(@PathVariable groupCode: String, @RequestBody dto: UpdateCommonGroupCodeRequest) =
        commonGroupCodeService.updateCommonGroupCode(groupCode, dto)

    @ApiOperation(value = "코드그룹 신규 생성")
    @PostMapping("/")
    fun createCommonGroupCode(@Valid @RequestBody dto: CreateCommonGroupCodeRequest) {
        commonGroupCodeService.createCommonGroupCode(dto)
    }
}