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

@Aspect
@Order(2)
@Component
class ServiceLogAspect(
    val objectMapper: ObjectMapper
) {

    companion object : Log

    /**
     *
     */
    @Pointcut(value = "execution(public * com.jaewoo.srs.app..service.*.*(..))")
    private fun serviceLog() {
    }

    @Before(value = "serviceLog()")
    fun deBefore(joinPoint: JoinPoint) {
        val args = joinPoint.args
        val codeSignature = joinPoint.signature as CodeSignature
        val paramNames = codeSignature.parameterNames
        val params = HashMap<String, Any>(args.size).toMutableMap()
        for (i in args.indices) {
            if (args[i] !is BindingResult) {
                params[paramNames[i]] = args[i]
            }
        }
        logger.debug("CALL={}; ARGS={}", joinPoint.signature.name, params)
    }

    @AfterReturning(returning = "ret", pointcut = "serviceLog()")
    @Throws(Throwable::class)
    fun doAfterReturning(ret: Any?) {
        logger.debug("RESPONSE={}", objectMapper.writeValueAsString(ret))
    }
}