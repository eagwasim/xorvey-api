package com.noubug.app.xorvey.infrastructure.web.security

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.inject.Named

@Named
class JwtTokenFilterConfigurer(private val jwtTokenProvider: JwtTokenProvider) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity?) {
        val customFilter = JwtTokenFilter(jwtTokenProvider)
        http!!.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}