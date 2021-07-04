package com.jaewoo.srs.core.config

import com.fasterxml.classmate.TypeResolver
import com.jaewoo.srs.core.security.properties.SecurityProperties
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseBuilder
import springfox.documentation.schema.AlternateTypeRules
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.time.YearMonth


@Configuration
class SwaggerConfig(
    private val securityProperties: SecurityProperties,
    private val typeResolver: TypeResolver
) {
    @Bean
    fun swagger(): Docket {
        val res404 = ResponseBuilder()
            .code("404")
            .description("Not Found")
            .build()

        return Docket(DocumentationType.SWAGGER_2)
            .alternateTypeRules(
                AlternateTypeRules.newRule(
                    typeResolver.resolve(Pageable::class.java),
                    typeResolver.resolve(Page::class.java)
                )
            )
            .directModelSubstitute(YearMonth::class.java, String::class.java)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.ant("/api/**"))
            .build()
            .useDefaultResponseMessages(false)
            .globalResponses(HttpMethod.GET, listOf(res404))
            .globalResponses(HttpMethod.POST, listOf(res404))
            .globalResponses(HttpMethod.PUT, listOf(res404))
            .globalResponses(HttpMethod.DELETE, listOf(res404))
            .apiInfo(this.metaInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
    }

    private fun apiKey(): ApiKey {
        return ApiKey("JWT", securityProperties.headerStringAccessToken, "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext
            .builder()
            .securityReferences(defaultAuth())
            .operationSelector { true }
            .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("JWT", authorizationScopes))
    }

    private fun metaInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("SRS Reservation System API Document")
            .description("SRS Reservation System REST API Spec Documentation")
            .version("1.0")
            .termsOfServiceUrl("https://www.srs.com")
            .contact(
                Contact(
                    "jaewoo jung",
                    "http://www.srs.com",
                    "jeawoo.jeong@samsung.com"
                )
            ).build()
    }

    @ApiModel(description = "페이지 요청")
    class Page(
        @ApiModelProperty(value = "페이지 번호(0..N)")
        val page: Int,

        @ApiModelProperty(value = "페이지 크기", allowableValues = "range[0, 100]")
        val size: Int,

        @ApiModelProperty(value = "정렬(사용법: 컬럼명,ASC|DESC)")
        val sort: List<String>
    )
}