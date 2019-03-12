package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.usecase.UserResetPassword
import javax.inject.Named

@Named
class UserResetPasswordImpl(private val userGateway: UserGateway) : UserResetPassword {

    override fun resetPassword(token: String, password: String) {
        userGateway.updateUserPasswordByToken(token, password)
    }
}