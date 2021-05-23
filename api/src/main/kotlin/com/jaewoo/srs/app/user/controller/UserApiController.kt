package com.jaewoo.srs.app.user.controller

import com.jaewoo.srs.app.user.domain.dto.CreateUserRequest
import com.jaewoo.srs.app.user.domain.dto.UpdateUserRequest
import com.jaewoo.srs.app.user.service.UserService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class UserApiController(
    private val userService: UserService
) {
    @GetMapping
    fun getUsersPageable(pageable: Pageable) = userService.getUsersPageable(pageable)

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long) = userService.getUser(userId)

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody dto: UpdateUserRequest) =
        userService.updateUser(userId, dto)

    @PostMapping
    fun createUser(@Valid @RequestBody dto: CreateUserRequest) {
        userService.createUser(dto)
    }
}