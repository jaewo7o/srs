package com.jaewoo.srs.core.logging

import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.CodeSignature
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import kotlin.collections.HashMap

@Aspect
@Order(5)
@Component
class WebLogAspect(
    val objectMapper: ObjectMapper
) {

    companion object : Log

    private val webRequestLog: ThreadLocal<WebLog> = ThreadLocal()

    /**
     * Define a cut-in, as long as it is for com.jaewoo.srs.app..controller public modification methods have to cut-in
     */
    @Pointcut(value = "execution(public * com.jaewoo.srs.app..controller.*.*(..))")
    fun webLog() {
    }

    /**
     * Connection Points of Aspects, and declare what needs to be done before the Connection Point enters
     */
    @Before(value = "webLog()")
    @Throws(Throwable::class)
    fun doBefore(joinPoint: JoinPoint) {
        val webLog = WebLog()
        webLog.startTime = System.currentTimeMillis()
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val request = attributes!!.request
        val args = joinPoint.args
        val paramNames = (joinPoint.signature as CodeSignature).parameterNames
        val params = HashMap<String, Any>(args.size)
        for (i in args.indices) {
            if (args[i] !is BindingResult) {
                params[paramNames[i]] = args[i]
            }
        }
        webLog.id = UUID.randomUUID().toString()
        webLog.request = params.toString()
        webLog.requestUrl = request.requestURI.toString()
        webLog.requestIp = request.remoteAddr
        webLog.method = request.method
        webRequestLog.set(webLog)
        logger.info(
            "REQUEST=${request.method} ${request.requestURL.toString()}; SOURCE IP=${request.remoteAddr}; ARGS=${params}"
        )
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    @Throws(Throwable::class)
    fun doAfterReturning(ret: Any?) {
        val webLog = webRequestLog.get()
        webLog.response = ret.toString()
        webLog.endTime = System.currentTimeMillis()
        webLog.time = webLog.endTime!! - webLog.startTime!!

        logger.info("RESPONSE={}; SPEND TIME={}MS", objectMapper.writeValueAsString(ret), webLog.time)
        logger.info("webLog:{}", webLog)

        webRequestLog.remove()
    }
}