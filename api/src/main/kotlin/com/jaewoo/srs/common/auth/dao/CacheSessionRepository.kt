package com.jaewoo.srs.common.auth.dao

import com.jaewoo.srs.common.auth.domain.entity.CacheSession
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CacheSessionRepository : CrudRepository<CacheSession, String> {
    override fun findById(id: String): Optional<CacheSession>
}