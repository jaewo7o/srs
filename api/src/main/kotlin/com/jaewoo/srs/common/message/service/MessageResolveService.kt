package com.jaewoo.srs.common.message.service

import org.springframework.stereotype.Service
import java.text.MessageFormat
import java.util.*

@Service
class MessageResolveService(
    val messageService: MessageService
) {

    fun resolveMessage(key: String, locale: Locale = Locale.KOREAN, vararg args: Any?): String {
        val message = messageService.getMessage(key)
        val i18nMessage = if (Locale.KOREAN.equals(locale)) message.contentsKo else message.contentsEn

        return when (args.isNullOrEmpty()) {
            true -> i18nMessage
            false -> MessageFormat.format(i18nMessage, *args)
        }
    }
}
