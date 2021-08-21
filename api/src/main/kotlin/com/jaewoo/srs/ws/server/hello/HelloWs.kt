package com.jaewoo.srs.ws.server.hello

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.xml.ws.RequestWrapper

@WebService(
    endpointInterface = "com.jaewoo.srs.ws.server.hello.HelloWs",
    serviceName = "HelloWs",
    portName = "HelloPort",
    targetNamespace = "http://www.srs.com"
)
interface HelloWs {

    @WebResult(name = "return2", targetNamespace = "")
    @WebMethod(action = "urn:SayHello2")
    @RequestWrapper(
        localName = "sayHello",
        targetNamespace = "http://www.srs.com",
        className = "com.jaewoo.srs.ws.server.hello.HelloWs"
    )
    fun hello(@WebParam(name = "name", targetNamespace = "") name: String): String
    fun register(@WebParam(name = "student") student: Student): String
}