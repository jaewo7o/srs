package com.jaewoo.srs.common.auth.dao

import com.jaewoo.srs.common.auth.domain.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
    fun findByRefreshToken(refreshToken: String): Optional<RefreshToken>
}