package com.jaewoo.srs.ws.server.hello

import javax.jws.WebService

@WebService(endpointInterface = "com.jaewoo.srs.ws.server.hello.HelloWs")
class HelloWsImpl : HelloWs {

    val counter: Int = 0

    override fun hello(name: String): String {
        return "Hello ${name}!!"
    }

    override fun register(student: Student): String {
        counter.inc()
        return "${student.name} is registered student number ${counter}"
    }
}