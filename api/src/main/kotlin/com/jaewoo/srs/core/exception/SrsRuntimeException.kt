package com.jaewoo.srs.core.exception

class SrsRuntimeException(val key: String) : Exception() {
    var params: String = ""

    constructor(key: String, params: String) : this(key) {
        this.params = params
    }
}