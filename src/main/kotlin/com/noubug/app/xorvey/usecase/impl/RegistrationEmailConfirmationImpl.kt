package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.usecase.EmailConfirmation
import javax.inject.Named

@Named
class EmailConfirmationImpl(private val userGateway: UserGateway) : EmailConfirmation {

    override fun confirmEmailToken(token: String): Boolean {
        return userGateway.confirmEmail(token)
    }
}