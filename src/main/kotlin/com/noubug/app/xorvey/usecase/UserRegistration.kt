package com.noubug.app.xorvey.usecase

import com.noubug.app.xorvey.usecase.model.UserRegistrationRequest
import com.noubug.app.xorvey.usecase.model.UserRegistrationResponse

interface UserRegistration {
    fun registerUser(userRegistrationRequest: UserRegistrationRequest): UserRegistrationResponse
}