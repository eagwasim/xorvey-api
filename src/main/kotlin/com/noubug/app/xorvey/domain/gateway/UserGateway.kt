package com.noubug.app.xorvey.domain.gateway

import com.noubug.app.xorvey.domain.entity.User
import com.noubug.app.xorvey.domain.model.AuthenticateUserRequest
import com.noubug.app.xorvey.domain.model.AuthenticateUserResponse
import com.noubug.app.xorvey.domain.model.PasswordResetToken
import com.noubug.app.xorvey.domain.model.RegisteredUser

interface UserGateway {
    fun registerUser(user: User): RegisteredUser
    fun confirmRegistrationEmail(token: String): Boolean
    fun confirmPasswordResetEmail(token: String): Boolean
    fun generatePasswordResetToken(email: String): PasswordResetToken
    fun updateUserPasswordByToken(token: String, password: String)
    fun authenticateUser(authenticateUserRequest: AuthenticateUserRequest): AuthenticateUserResponse
}