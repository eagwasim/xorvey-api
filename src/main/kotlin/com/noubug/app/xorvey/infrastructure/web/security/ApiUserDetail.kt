package com.noubug.app.xorvey.infrastructure.web.security

import com.noubug.app.xorvey.usecase.GetUserDetailByEmail
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class ApiUserDetail(private val getUserDetailByEmail: GetUserDetailByEmail) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val accessUserDetail = getUserDetailByEmail.getUserDetails(username!!)
        return User(accessUserDetail.email, accessUserDetail.authKey, null)
    }
}