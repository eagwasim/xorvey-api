package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.usecase.PasswordResetEmailConfirmation
import javax.inject.Named

@Named
class PasswordResetEmailConfirmationImpl(private val userGateway: UserGateway) : PasswordResetEmailConfirmation {
    override fun confirmEmailToken(token: String): Boolean {
        return userGateway.confirmPasswordResetEmail(token)
    }
}