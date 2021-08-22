package com.jaewoo.srs.ws.server.hello

import javax.jws.WebParam
import javax.jws.WebService

@WebService
interface HelloWs {
    fun hello(@WebParam(name = "name", targetNamespace = "") name: String): String
    fun register(@WebParam(name = "student") student: Student): String
}