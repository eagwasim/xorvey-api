package com.noubug.app.xorvey.infrastructure.web.security

import com.noubug.app.xorvey.usecase.AccessGetUserDetailByEmail
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class ApiUserDetail(private val accessGetUserDetailByEmail: AccessGetUserDetailByEmail) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val accessUserDetail = accessGetUserDetailByEmail.getUserDetails(username!!)
        return User(accessUserDetail.email, accessUserDetail.authKey, null)
    }
}