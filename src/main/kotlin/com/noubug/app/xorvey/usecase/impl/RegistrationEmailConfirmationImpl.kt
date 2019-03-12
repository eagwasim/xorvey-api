package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.usecase.RegistrationEmailConfirmation
import javax.inject.Named

@Named
class RegistrationEmailConfirmationImpl(private val userGateway: UserGateway) : RegistrationEmailConfirmation {

    override fun confirmEmailToken(token: String): Boolean {
        return userGateway.confirmRegistrationEmail(token)
    }
}