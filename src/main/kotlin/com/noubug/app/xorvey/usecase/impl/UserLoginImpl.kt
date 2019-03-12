package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.domain.model.AuthenticateUserRequest
import com.noubug.app.xorvey.usecase.UserLogin
import javax.inject.Named

@Named
class UserLoginImpl(private val userGateway: UserGateway) : UserLogin {

    override fun login(email: String, password: String): String {
        val authenticateUserResponse = userGateway.authenticateUser(AuthenticateUserRequest(email, password))
        return authenticateUserResponse.token
    }
}