package com.jaewoo.srs.app.test.controller

import com.jaewoo.srs.SpringWebTestSupport
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.get

internal class TestApiControllerTest : SpringWebTestSupport() {

    @Test
    fun `hello world test`() {
        // given
        val name: String = "Jaewoo"

        // when & then
        mockMvc.get("/api/hello") {
            param("name", name)
        }.andExpect {
            status { isOk() }
        }.andDo {
            print()
        }
    }
}