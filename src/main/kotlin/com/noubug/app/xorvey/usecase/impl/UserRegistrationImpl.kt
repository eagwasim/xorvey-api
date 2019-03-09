package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.usecase.UserRegistration
import com.noubug.app.xorvey.usecase.model.UserRegistrationRequest
import com.noubug.app.xorvey.usecase.model.UserRegistrationResponse
import javax.inject.Named

@Named
class UserRegistrationImpl : UserRegistration {
    override fun registerUser(userRegistrationRequest: UserRegistrationRequest): UserRegistrationResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}