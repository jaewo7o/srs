package com.jaewoo.srs.ws.server

import com.jaewoo.srs.core.util.WsUtil
import com.jaewoo.srs.ws.server.hello.HelloWs
import org.assertj.core.api.Assertions


class HelloWsTest {

    //@Test
    fun `Hello Webservice Test`() {
        val address = "http://localhost:8080/services/hello"
        val helloWsProxy = WsUtil.getProxyService(address, HelloWs::class.java)

        val result = helloWsProxy.hello("jaewoo")
        println("result = $result")
        Assertions.assertThat(result).isEqualTo("Hello jaewoo!!")
    }
}