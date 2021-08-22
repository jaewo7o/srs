package com.jaewoo.srs.core.config

import com.jaewoo.srs.common.auth.service.CacheSessionService
import com.jaewoo.srs.core.filter.JwtAuthenticationFilter
import com.jaewoo.srs.core.security.jwt.JwtAccessDeniedHandler
import com.jaewoo.srs.core.security.jwt.JwtAuthenticationEntryPoint
import com.jaewoo.srs.core.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity // 스프링시큐리티 필터가 스프링 필터체인에 등록됨
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val cacheSessionService: CacheSessionService,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun encodePassword() = BCryptPasswordEncoder()

    /**
     * 스프링 시큐리티 룰을 무시하게 하는 Url 규칙(여기 등록하면 규칙 적용하지 않음)
     */
    override fun configure(web: WebSecurity) {
        web.ignoring()
            // Soap Webservice 예외처리
            .antMatchers("/services/**")
            // swagger 예외처리
            .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable() // rest api 임으로 기본설정 사용안함
            .csrf().disable() // rest api 이기 때문에 csrf 보안 설정 필요 없음
            .cors()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            // 인증 예외처리
            .antMatchers("/*/signin", "/api/reissue-token", "/api/anonymous/**").permitAll()
            .antMatchers("/api/**").authenticated()

            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider, cacheSessionService),
                UsernamePasswordAuthenticationFilter::class.java
            )
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf(GET.name, PUT.name, POST.name, DELETE.name)
            allowedHeaders = listOf("*")
            allowCredentials = true
            maxAge = 3600L
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}