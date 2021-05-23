package com.jaewoo.srs.app.test.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Api(value = "Test Rest API")
@RestController
class TestApiController {

    @ApiOperation(value = "Hello World Test", notes = "REST API 테스트 예제입니다.")
    @GetMapping("/api/hello")
    fun hello(
        @ApiParam(value = "이름", required = true, example = "테스트이름")
        @RequestParam("name") name: String
    ): String {
        return "hello world 99${name}"
    }
}