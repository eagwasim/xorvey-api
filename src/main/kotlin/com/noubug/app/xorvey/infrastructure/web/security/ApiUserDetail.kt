package com.noubug.app.xorvey.infrastructure.web.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class ApiUserDetail : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}