package com.jaewoo.srs.core.config

import com.jaewoo.srs.core.filter.JwtAuthenticationFilter
import com.jaewoo.srs.core.security.jwt.JwtAccessDeniedHandler
import com.jaewoo.srs.core.security.jwt.JwtAuthenticationEntryPoint
import com.jaewoo.srs.core.security.jwt.JwtTokenProvider
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
    private val corsFilter: CorsFilter,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun encodePassword() = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable() // rest api 임으로 기본설정 사용안함
            .csrf().disable() // rest api 이기 때문에 csrf 보안 설정 필요 없음
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

            .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                //.antMatchers("/*/signin", "/api/anonymous/**").permitAll()
                // swagger 예외처리
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/api/**").authenticated()

            .and()
                .addFilterBefore(corsFilter, CsrfFilter::class.java)
                .addFilterBefore(
                    JwtAuthenticationFilter(jwtTokenProvider),
                    UsernamePasswordAuthenticationFilter::class.java
                )
    }
}