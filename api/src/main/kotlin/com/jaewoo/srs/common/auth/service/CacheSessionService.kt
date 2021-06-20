package com.jaewoo.srs.common.auth.service

import com.jaewoo.srs.common.auth.dao.CacheSessionRepository
import com.jaewoo.srs.common.auth.domain.entity.CacheSession
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import org.springframework.stereotype.Service

@Service
class CacheSessionService(
    private val cacheSessionRepository: CacheSessionRepository
) {
    fun getSession(sessionId: String) =
        getOptSession(sessionId).orElseThrow { SrsDataNotFoundException() }

    fun getOptSession(sessionId: String) =
        cacheSessionRepository.findById(sessionId)

    fun saveSession(cacheSession: CacheSession) = cacheSessionRepository.save(cacheSession)
}