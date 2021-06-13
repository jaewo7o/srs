package com.jaewoo.srs.common.message.dao

import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.common.message.domain.entity.QMessage
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.util.*

interface MessageRepository : JpaRepository<Message, Long> {
    fun existsByKey(key: String): Boolean
    fun findByKey(key: String): Optional<Message>
}

@Repository
class MessageRepositorySupport : QuerydslRepositorySupport(Message::class.java) {
    fun findAllPage(predicate: Predicate?, pageable: Pageable): Page<Message> {
        val table = QMessage.message
        val sql = from(table)
            .where(predicate)
            .orderBy(table.id.desc())

        val queryResult = querydsl!!.applyPagination(pageable, sql).fetch()

        return PageImpl(queryResult, pageable, sql.fetchCount())
    }
}

class MessagePredicator {
    companion object {
        private val table = QMessage.message
    }

    private var builder = BooleanBuilder()

    fun key(key: String?): MessagePredicator {
        if (!key.isNullOrBlank()) {
            builder.and(table.key.eq(key))
        }

        return this
    }

    fun contents(contents: String?): MessagePredicator {
        if (!contents.isNullOrBlank()) {
            builder.and(
                table.contentsKo.contains(contents).or(
                    table.contentsEn.contains(contents)
                )
            )
        }

        return this
    }

    fun value() = builder.value
}