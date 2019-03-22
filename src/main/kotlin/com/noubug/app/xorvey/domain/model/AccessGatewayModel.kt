package com.noubug.app.xorvey.domain.model

import com.noubug.app.xorvey.domain.entity.Access

data class RegisteredUser(val userId: Long, val emailVerificationToken: String)
data class PasswordResetToken(val name: String, val token: String)
data class AuthenticateUserRequest(val email: String, val password: String)
data class AuthenticateUserResponse(val access: Access, val token: String)