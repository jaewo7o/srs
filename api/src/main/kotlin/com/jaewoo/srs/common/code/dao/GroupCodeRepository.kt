package com.jaewoo.srs.common.code.dao

import com.jaewoo.srs.common.code.domain.entity.GroupCode
import com.jaewoo.srs.common.code.domain.entity.QGroupCode
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

interface GroupCodeRepository : JpaRepository<GroupCode, String> {
}

@Repository
class GroupCodeRepositorySupport : QuerydslRepositorySupport(GroupCode::class.java) {
    fun findAllPage(predicate: Predicate?, pageable: Pageable): Page<GroupCode> {
        val table = QGroupCode.groupCode1
        val sql = from(table)
            .where(predicate)

        val queryResult = querydsl!!.applyPagination(pageable, sql).fetch()

        return PageImpl(queryResult, pageable, sql.fetchCount())
    }
}

class GroupCodePredicator {
    companion object {
        private val table = QGroupCode.groupCode1
    }

    private var builder = BooleanBuilder()

    fun groupCodeName(groupCodeName: String?): GroupCodePredicator {
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