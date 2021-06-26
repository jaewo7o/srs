package com.jaewoo.srs.core.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

object DateUtil {
    fun convertZoneDateTime(
        localDateTime: LocalDateTime,
        toZoneId: ZoneId,
        fromZoneId: ZoneId = ZoneId.of("Asia/Seoul")
    ): ZonedDateTime {
        return localDateTime.atZone(fromZoneId)
            .withZoneSameInstant(toZoneId)
    }
}