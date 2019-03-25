package com.noubug.app.xorvey.domain.gateway

import com.noubug.app.xorvey.domain.entity.Access
import com.noubug.app.xorvey.domain.model.*

interface AccessGateway {
    fun registerUser(access: Access): RegisteredUser
    fun confirmRegistrationEmail(token: String): Boolean
    fun confirmPasswordResetEmail(token: String): Boolean
    fun generatePasswordResetToken(email: String): PasswordResetToken
    fun updateUserPasswordByToken(token: String, password: String)
    fun authenticateUser(authenticateUserRequest: AuthenticateUserRequest): AuthenticateUserResponse
}