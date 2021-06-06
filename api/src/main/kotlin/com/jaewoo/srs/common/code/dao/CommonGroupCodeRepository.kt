package com.jaewoo.srs.common.code.dao

import com.jaewoo.srs.common.code.domain.entity.CommonGroupCode
import com.jaewoo.srs.common.code.domain.entity.QCommonGroupCode
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

interface CommonGroupCodeRepository : JpaRepository<CommonGroupCode, String> {
}

@Repository
class CommonGroupCodeRepositorySupport : QuerydslRepositorySupport(CommonGroupCode::class.java) {
    fun findAllPage(predicate: Predicate?, pageable: Pageable): Page<CommonGroupCode> {
        val table = QCommonGroupCode.commonGroupCode
        val sql = from(table)
            .where(predicate)

        val queryResult = querydsl!!.applyPagination(pageable, sql).fetch()

        return PageImpl(queryResult, pageable, sql.fetchCount())
    }
}

class CommonGroupCodePredicator {
    companion object {
        private val table = QCommonGroupCode.commonGroupCode
    }

    private var builder = BooleanBuilder()

    fun groupCodeName(groupCodeName: String?): CommonGroupCodePredicator {
        if (!groupCodeName.isNullOrBlank()) {
            builder.and(
                table.groupCodeNameKo.contains(groupCodeName).or(
                    table.groupCodeNameEn.contains(groupCodeName)
                )
            )
        }

        return this
    }

    fun value() = builder.value
}