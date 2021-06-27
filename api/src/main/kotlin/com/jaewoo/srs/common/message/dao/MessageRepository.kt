package com.jaewoo.srs.common.message.dao

import com.jaewoo.srs.app.user.domain.entity.QUser
import com.jaewoo.srs.common.message.domain.dto.SearchMessageResponse
import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.common.message.domain.entity.QMessage
import com.jaewoo.srs.core.context.SrsContext
import com.jaewoo.srs.core.util.DateUtil
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.ZoneId
import java.util.*
import kotlin.streams.toList

interface MessageRepository : JpaRepository<Message, Long> {
    fun existsByKey(key: String): Boolean
    fun findByKey(key: String): Optional<Message>
}

@Repository
class MessageRepositorySupport(val query: JPAQueryFactory) : QuerydslRepositorySupport(Message::class.java) {
    fun findAllPage(predicate: Predicate?, pageable: Pageable): Page<SearchMessageResponse> {
        val message = QMessage.message
        val user = QUser.user

        val sql = query.select(message)
            .from(message)
            .join(user).on(message.updatedUserId.eq(user.id))
            .where(
                predicate
            )
            .orderBy(message.id.desc())

        val currentUser = SrsContext.getCurrentUser()

        val queryResult = querydsl!!.applyPagination(pageable, sql).fetch()
        val convertQueryResult = queryResult.stream()
            .map {
                SearchMessageResponse(
                    id = it.id!!,
                    key = it.key,
                    messageType = it.messageType,
                    contentsKo = it.contentsKo,
                    contentsEn = it.contentsEn,
                    updatedUserName = "",
                    updatedAt = DateUtil.convertZoneDateTime(it.updatedAt, ZoneId.of(currentUser.timezoneName))
                )
            }
            .toList()

        return PageImpl(convertQueryResult, pageable, sql.fetchCount())
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