package com.jaewoo.srs.ws.server.hello

import javax.jws.WebService

@WebService
interface HelloWs {
    fun hello(name: String): String
    fun register(student: Student): String
}