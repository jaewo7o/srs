package com.jaewoo.srs.app.user.service

import com.jaewoo.srs.app.user.dao.UserRepository
import com.jaewoo.srs.app.user.domain.dto.CreateUserRequest
import com.jaewoo.srs.app.user.domain.dto.UpdateUserRequest
import com.jaewoo.srs.app.user.domain.entity.User
import com.jaewoo.srs.core.exception.SrsRuntimeException
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val encodePassword: BCryptPasswordEncoder
) {
    fun getUser(userId: Long): User {
        return userRepository.findById(userId).orElseThrow {
            throw SrsRuntimeException("MSG0004")
        }
    }

    fun getUserByLoginId(loginId: String): User {
        return userRepository.findByLoginId(loginId).orElseThrow {
            throw SrsRuntimeException("MSG0004")
        }
    }

    @Transactional
    fun createUser(dto: CreateUserRequest): User {
        val saveUser = dto.toEntity().also {
            it.password = encodePassword.encode(it.password)
        }

        userRepository.findByLoginId(saveUser.loginId)
            .ifPresent { throw SrsRuntimeException("MSG0001") }

        return userRepository.save(saveUser)
    }

    fun getUsersPageable(pageable: Pageable) = userRepository.findAll(pageable)

    @Transactional
    fun updateUser(userId: Long, dto: UpdateUserRequest) {
        getUser(userId).apply {
            this.name = dto.name
            this.mobileNo = dto.mobileNo
        }
    }

}