package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.AccessGateway
import com.noubug.app.xorvey.usecase.AccessResetPassword
import javax.inject.Named

@Named
class AccessResetPasswordImpl(private val accessGateway: AccessGateway) : AccessResetPassword {

    override fun resetPassword(token: String, password: String) {
        accessGateway.updateUserPasswordByToken(token, password)
    }
}