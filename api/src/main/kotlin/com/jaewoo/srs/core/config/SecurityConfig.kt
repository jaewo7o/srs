package com.jaewoo.srs.core.config

import com.jaewoo.srs.core.config.jwt.JwtAccessDeniedHandler
import com.jaewoo.srs.core.config.jwt.JwtAuthenticationEntryPoint
import com.jaewoo.srs.core.config.jwt.JwtAuthenticationFilter
import com.jaewoo.srs.core.config.jwt.JwtTokenProvider
import com.jaewoo.srs.core.config.properties.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.cors.CorsUtils
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity // 스프링시큐리티 필터가 스프링 필터체인에 등록됨
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val securityProperties: SecurityProperties,
    private val corsFilter: CorsFilter,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun encodePassword() = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable() // rest api 임으로 기본설정 사용안함
            .csrf().disable() // rest api 이기 때문에 csrf 보안 설정 필요 없음
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/*/signin", "/*/signup").permitAll()
            // swagger 예외처리
            .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
            .antMatchers("/api/**").authenticated()

            .and()
            .addFilterBefore(corsFilter, CsrfFilter::class.java)
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider, securityProperties),
                UsernamePasswordAuthenticationFilter::class.java
            )
    }
}