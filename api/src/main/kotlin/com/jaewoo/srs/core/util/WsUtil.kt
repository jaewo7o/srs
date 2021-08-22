@file:Suppress("UNCHECKED_CAST")

package com.jaewoo.srs.core.util

import org.apache.cxf.frontend.ClientProxy
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean
import org.apache.cxf.transport.http.HTTPConduit
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy

object WsUtil {
    fun <T> getProxyService(
        address: String,
        serviceClass: Class<T>
    ): T {
        val factory = JaxWsProxyFactoryBean()
        factory.address = address
        factory.serviceClass = serviceClass
        val proxyClass = factory.create() as T

        val policy = HTTPClientPolicy()
        policy.connectionTimeout = 1000L
        policy.receiveTimeout = 10000L

        val client = ClientProxy.getClient(proxyClass)
        val conduit = client.conduit as HTTPConduit
        conduit.client = policy
        return proxyClass
    }
}