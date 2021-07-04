package com.jaewoo.srs.common.message.dao

import com.jaewoo.srs.app.user.domain.entity.QUser.user
import com.jaewoo.srs.common.message.domain.dto.QSearchMessageResponse
import com.jaewoo.srs.common.message.domain.dto.SearchMessageRequest
import com.jaewoo.srs.common.message.domain.dto.SearchMessageResponse
import com.jaewoo.srs.common.message.domain.entity.QMessage.message
import com.jaewoo.srs.core.context.SrsContext
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

interface MessageDao {
    fun findAllPage(dto: SearchMessageRequest, pageable: Pageable): Page<SearchMessageResponse>
}

@Repository
class MessageDaoImpl(val query: JPAQueryFactory) : MessageDao {

    override fun findAllPage(dto: SearchMessageRequest, pageable: Pageable): Page<SearchMessageResponse> {
        val currentUser = SrsContext.getCurrentUser()

        val queryResults = query
            .select(
                QSearchMessageResponse(
                    message.id,
                    message.key,
                    Expressions.asString("UT"),
                    message.contentsKo,
                    message.contentsEn,
                    user.name,
                    message.updatedAt
                    //DateUtil.convertZoneDateTime(message.updatedAt, ZoneId.of(currentUser.timezoneName))
                )
            )
            .from(message)
            .join(user).on(message.updatedUserId.eq(user.id))
            .where(
                eqKey(dto.key),
                containContents(dto.contents)
            )
            .orderBy(message.id.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()

        return PageImpl(queryResults.results, pageable, queryResults.total)
    }

    fun eqKey(key: String?): BooleanExpression? {
        return key?.let {
            message.key.eq(key)
        }
    }

    fun containContents(contents: String?): BooleanExpression? {
        return contents?.let {
            message.contentsKo.contains(contents)
                .or(message.contentsEn.contains(contents))
        }
    }
}