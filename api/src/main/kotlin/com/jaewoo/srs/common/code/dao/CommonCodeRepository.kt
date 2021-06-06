package com.jaewoo.srs.common.code.dao

import com.jaewoo.srs.common.code.domain.entity.CommonCode
import org.springframework.data.jpa.repository.JpaRepository

interface CommonCodeRepository : JpaRepository<CommonCode, String> {
}