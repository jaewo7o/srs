package com.jaewoo.srs.core.logging.sql

import com.p6spy.engine.spy.P6SpyOptions
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct


@Configuration
class P6spyLogMessageFormatConfiguration {
    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6spySqlFormatConfiguration::class.java.getName()
    }
}