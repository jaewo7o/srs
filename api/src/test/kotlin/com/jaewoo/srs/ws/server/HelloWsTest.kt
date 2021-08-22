package com.jaewoo.srs.ws.server

import com.jaewoo.srs.ws.server.hello.HelloWs
import org.apache.cxf.frontend.ClientProxy
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean
import org.apache.cxf.transport.http.HTTPConduit
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class HelloWsTest {

    @Test
    fun `Hello Webservice Test`() {
        val factory = JaxWsProxyFactoryBean()
        factory.address = "http://localhost:8080/services/hello"
        factory.serviceClass = HelloWs::class.java
        val helloWsProxy = factory.create() as HelloWs

        val policy = HTTPClientPolicy()
        policy.connectionTimeout = 1000L
        policy.receiveTimeout = 10000L

        val client = ClientProxy.getClient(helloWsProxy)
        val conduit = client.conduit as HTTPConduit
        conduit.client = policy

        val result = helloWsProxy.hello("jaewoo")
        Assertions.assertThat(result).isEqualTo("Hello jaewoo!!")
    }
}