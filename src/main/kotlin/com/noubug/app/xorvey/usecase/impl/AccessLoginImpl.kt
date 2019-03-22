package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.AccessGateway
import com.noubug.app.xorvey.domain.model.AuthenticateUserRequest
import com.noubug.app.xorvey.usecase.AccessLogin
import javax.inject.Named

@Named
class AccessLoginImpl(private val accessGateway: AccessGateway) : AccessLogin {

    override fun login(email: String, password: String): String {
        val authenticateUserResponse = accessGateway.authenticateUser(AuthenticateUserRequest(email, password))
        return authenticateUserResponse.token
    }
}