package com.jaewoo.srs.app.user.dao

import com.jaewoo.srs.app.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByLoginId(loginId: String): Optional<User>
}