package com.jaewoo.srs.common.auth.service

import com.jaewoo.srs.app.user.domain.entity.User
import com.jaewoo.srs.app.user.service.UserService
import com.jaewoo.srs.core.exception.SrsRuntimeException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val userService: UserService,
    private val encodePassword: BCryptPasswordEncoder,
) {
    fun signin(loginId: String, password: String): User {
        val findUser = userService.getUserByLoginId(loginId)

        if (!encodePassword.matches(password, findUser.password)) {
            throw SrsRuntimeException("MSG0005")
        }

        return findUser
    }
}