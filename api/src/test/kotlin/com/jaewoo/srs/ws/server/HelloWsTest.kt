package com.jaewoo.srs.ws.server

import com.jaewoo.srs.core.config.WebServiceConfig
import com.jaewoo.srs.ws.server.hello.HelloWs
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext


class HelloWsTest {
    private val context: ApplicationContext = AnnotationConfigApplicationContext(WebServiceConfig::class.java)
    private val helloWsProxy: HelloWs = context.getBean("client") as HelloWs

    @Test
    fun `Hello Webservice Test`() {
        val result = helloWsProxy.hello("jaewoo")
        Assertions.assertThat(result).isEqualTo("Hello jaewoo!!")
    }
}