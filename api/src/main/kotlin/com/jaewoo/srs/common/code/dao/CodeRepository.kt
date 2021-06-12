package com.jaewoo.srs.common.code.dao

import com.jaewoo.srs.common.code.domain.entity.Code
import com.jaewoo.srs.common.code.domain.entity.CodeKey
import org.springframework.data.jpa.repository.JpaRepository

interface CodeRepository : JpaRepository<Code, CodeKey>