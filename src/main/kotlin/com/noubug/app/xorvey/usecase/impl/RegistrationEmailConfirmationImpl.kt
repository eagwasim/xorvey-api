package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.AccessGateway
import com.noubug.app.xorvey.usecase.RegistrationEmailConfirmation
import javax.inject.Named

@Named
class RegistrationEmailConfirmationImpl(private val accessGateway: AccessGateway) : RegistrationEmailConfirmation {

    override fun confirmEmailToken(token: String): Boolean {
        return accessGateway.confirmRegistrationEmail(token)
    }
}