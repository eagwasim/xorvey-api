package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.AccessGateway
import com.noubug.app.xorvey.usecase.PasswordResetEmailConfirmation
import javax.inject.Named

@Named
class PasswordResetEmailConfirmationImpl(private val accessGateway: AccessGateway) : PasswordResetEmailConfirmation {
    override fun confirmEmailToken(token: String): Boolean {
        return accessGateway.confirmPasswordResetEmail(token)
    }
}