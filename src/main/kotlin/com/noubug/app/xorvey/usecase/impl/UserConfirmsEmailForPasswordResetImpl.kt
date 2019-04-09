package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.AccessGateway
import com.noubug.app.xorvey.usecase.UserConfirmsEmailForPasswordReset
import javax.inject.Named

@Named
class UserConfirmsEmailForPasswordResetImpl(private val accessGateway: AccessGateway) : UserConfirmsEmailForPasswordReset {
    override fun confirmEmailToken(token: String): Boolean {
        return accessGateway.confirmPasswordResetEmail(token)
    }
}