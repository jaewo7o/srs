package com.jaewoo.srs.core.logging.sql

import com.p6spy.engine.common.P6Util
import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle
import java.util.*


class P6spySqlFormatConfiguration : MessageFormattingStrategy {
    override fun formatMessage(
        connectionId: Int,
        now: String,
        elapsed: Long,
        category: String,
        prepared: String,
        sql: String,
        url: String
    ): String {
        var formatSql = formatSql(category, sql)
        return now + "|" + elapsed + "ms|" + category + "|connection " + connectionId + "|" + P6Util.singleLine(prepared) + formatSql
    }

    private fun formatSql(category: String, sql: String?): String? {
        if (sql.isNullOrBlank()) return sql

        var formatSql: String?
        // Only format Statement, distinguish DDL And DML
        if (Category.STATEMENT.getName().equals(category)) {
            val tmpsql = sql.trim { it <= ' ' }.toLowerCase(Locale.ROOT)
            if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                formatSql = FormatStyle.DDL.getFormatter().format(sql)
            } else {
                formatSql = FormatStyle.BASIC.getFormatter().format(sql)
            }
            return "|\nHeFormatSql(P6Spy sql, Hibernate format):$formatSql"
        }
        return sql
    }
}