package com.noubug.app.xorvey.infrastructure.web.config

import com.noubug.app.xorvey.infrastructure.web.security.JwtTokenFilterConfigurer
import com.noubug.app.xorvey.infrastructure.web.security.JwtTokenProvider
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy


@Configuration
class WebSecurityConfig(val jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http!!.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests()
                .antMatchers("/api/v1/access/login").permitAll()
                .antMatchers("/api/v1/access/register").permitAll()
                .antMatchers("/api/v1/access/password/forgot").permitAll()
                .antMatchers("/api/v1/access/password/reset").permitAll()
                .antMatchers("/web/verify/email/reg").permitAll()
                .anyRequest().authenticated()

        http.apply(JwtTokenFilterConfigurer(jwtTokenProvider))
    }
}