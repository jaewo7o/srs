package com.jaewoo.srs.core.test

import com.jaewoo.srs.common.auth.domain.vo.SessionUser
import com.jaewoo.srs.core.context.SrsContext
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.io.ResourceLoader
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@AutoConfigureMockMvc
@Import(RestDocsConfiguration::class)
@ExtendWith(RestDocumentationExtension::class)
class SpringWebTestSupport : SpringTestSupport() {

    @Autowired
    protected lateinit var restdocs: RestDocsConfiguration

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var write: RestDocumentationResultHandler

    @Autowired
    protected lateinit var resourceLoader: ResourceLoader

    protected val classpath = "classpath:"

    companion object {
        @BeforeAll
        @JvmStatic
        fun initSession() {
            var sessionUser = SessionUser(
                4L,
                name = "Jung Jaewoo",
                mobileNo = "010-9910-2227",
                loginId = "jeawoo.jeong@gmail.com",
                password = ""
            )

            SrsContext.setCurrentUser(sessionUser)
        }
    }

    @BeforeEach
    fun setUp(context: WebApplicationContext, provider: RestDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(provider))
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(provider))
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .alwaysDo<DefaultMockMvcBuilder>(restdocs.write())
            .build()
    }
}

@Configuration
class RestDocsConfiguration {
    @Bean
    fun write(): RestDocumentationResultHandler {
        return MockMvcRestDocumentation.document(
            "{class-name}/{method-name}",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        )
    }
}