package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.AccessGateway
import com.noubug.app.xorvey.usecase.UserResetsPassword
import javax.inject.Named

@Named
class UserResetsPasswordImpl(private val accessGateway: AccessGateway) : UserResetsPassword {

    override fun resetPassword(token: String, password: String) {
        accessGateway.updateUserPasswordByToken(token, password)
    }
}