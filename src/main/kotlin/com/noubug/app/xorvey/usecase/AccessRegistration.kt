package com.noubug.app.xorvey.usecase

import com.noubug.app.xorvey.usecase.model.UserRegistrationRequest
import com.noubug.app.xorvey.usecase.model.UserRegistrationResponse

interface AccessRegistration {
    fun registerUser(userRegistrationRequest: UserRegistrationRequest): UserRegistrationResponse
}