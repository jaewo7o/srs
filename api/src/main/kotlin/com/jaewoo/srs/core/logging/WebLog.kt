package com.jaewoo.srs.core.logging

import com.fasterxml.jackson.databind.ObjectMapper

class WebLog {
    var id: String = ""
    var request: String? = null
    var response: String? = null
    var time: Long? = null
    var requestUrl: String? = null
    var requestIp: String? = null
    var startTime: Long? = null
    var endTime: Long? = null
    var method: String? = null

    override
    fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}