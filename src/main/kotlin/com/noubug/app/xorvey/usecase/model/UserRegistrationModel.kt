package com.noubug.app.xorvey.usecase.model

import com.noubug.app.xorvey.domain.entity.Account
import com.noubug.app.xorvey.domain.entity.User

data class UserRegistrationRequest(val firstName: String, val lastName: String, val email: String, val password: String, val accountType: String, val accountName: String)
data class UserRegistrationResponse(val userId: Long)

fun UserRegistrationRequest.toUser() = User(null, this.firstName, this.lastName, this.email, null, false, this.password, Account(this.accountName, this.accountType))