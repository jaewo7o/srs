package com.jaewoo.srs.core.config

import com.jaewoo.srs.ws.server.hello.HelloWs
import com.jaewoo.srs.ws.server.hello.HelloWsImpl
import org.apache.cxf.Bus
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.xml.ws.Endpoint


@Configuration
class WebServiceConfig(
) {
    @Bean(name = arrayOf(Bus.DEFAULT_BUS_ID))
    fun springBus() = SpringBus()

    @Bean
    fun endpoint(): Endpoint {
        val endpoint = EndpointImpl(springBus(), HelloWsImpl())
        endpoint.publish("http://localhost:8080/services/hello")
        return endpoint
    }

    @Bean(name = ["client"])
    fun generateProxy() = proxyFactoryBean().create()

    @Bean
    fun proxyFactoryBean(): JaxWsProxyFactoryBean {
        val proxyFactory = JaxWsProxyFactoryBean()
        proxyFactory.serviceClass = HelloWs::class.java
        proxyFactory.address = "http://localhost:8080/services/hello"
        return proxyFactory
    }
}