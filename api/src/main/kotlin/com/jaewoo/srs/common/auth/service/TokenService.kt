package com.jaewoo.srs.common.auth.service

import com.jaewoo.srs.common.auth.dao.RefreshTokenRepository
import com.jaewoo.srs.common.auth.domain.entity.RefreshToken
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun getRefreshToken(refreshToken: String) =
        refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow { SrsDataNotFoundException() }

    fun saveRefreshToken(refreshToken: RefreshToken) = refreshTokenRepository.save(refreshToken)
}