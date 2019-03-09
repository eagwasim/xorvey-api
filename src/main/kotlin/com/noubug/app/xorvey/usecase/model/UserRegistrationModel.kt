package com.noubug.app.xorvey.usecase.model

data class UserRegistrationRequest(val firstName: String, val lastName: String, val email: String, val password: String, val accountType: String, val accountName: String)
data class UserRegistrationResponse(val userId: Long)