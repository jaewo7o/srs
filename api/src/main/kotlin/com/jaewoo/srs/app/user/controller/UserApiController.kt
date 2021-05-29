package com.jaewoo.srs.app.user.controller

import com.jaewoo.srs.app.user.domain.dto.CreateUserRequest
import com.jaewoo.srs.app.user.domain.dto.UpdateUserRequest
import com.jaewoo.srs.app.user.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(value = "사용자관리 REST API")
@RestController
class UserApiController(
    private val userService: UserService
) {
    @ApiOperation(value = "사용자 검색")
    @GetMapping("/api/users")
    fun getUsersPageable(pageable: Pageable) = userService.getUsersPageable(pageable)

    @ApiOperation(value = "사용자 단건 조회")
    @GetMapping("/api/users/{userId}")
    fun getUser(@PathVariable userId: Long) = userService.getUser(userId)

    @ApiOperation(value = "사용자 단건 수정")
    @PutMapping("/api/users/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody dto: UpdateUserRequest) =
        userService.updateUser(userId, dto)

    @ApiOperation(value = "사용자 신규 생성")
    @PostMapping(value = ["/api/users", "/api/anonymous/users"])
    fun createUser(@Valid @RequestBody dto: CreateUserRequest) {
        userService.createUser(dto)
    }
}