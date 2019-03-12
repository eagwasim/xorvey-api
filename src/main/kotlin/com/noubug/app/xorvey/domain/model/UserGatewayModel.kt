package com.noubug.app.xorvey.domain.model

import com.noubug.app.xorvey.domain.entity.User

data class RegisteredUser(val userId: Long, val emailVerificationToken: String)
data class PasswordResetToken(val name: String, val token: String)
data class AuthenticateUserRequest(val email: String, val password: String)
data class AuthenticateUserResponse(val user: User, val token: String)