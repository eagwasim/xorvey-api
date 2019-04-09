package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.AccessGateway
import com.noubug.app.xorvey.usecase.UserConfirmsEmailAfterRegistration
import javax.inject.Named

@Named
class UserConfirmsEmailAfterRegistrationImpl(private val accessGateway: AccessGateway) : UserConfirmsEmailAfterRegistration {

    override fun confirmEmailToken(token: String): Boolean {
        return accessGateway.confirmRegistrationEmail(token)
    }
}