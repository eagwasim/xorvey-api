package com.noubug.app.xorvey.infrastructure.web.model

import com.noubug.app.xorvey.usecase.model.UserRegistrationRequest

data class UserRegistrationRequestJSON(val firstName: String, val lastName: String, val email: String, val password: String, val accountType: String, val accountName: String)
data class UserRegistrationResponseJSON(val userId: Long)
class UserLoginRequestJSON()
class UserLoginResponseJSON()
class UserForgotPasswordRequestJSON()
class UserForgotPasswordResponseJSON()
class UserResetPasswordRequestJSON()
class UserResetPasswordResponseJSON()

fun UserRegistrationRequestJSON.toRequest() = UserRegistrationRequest(this.firstName, this.lastName, this.email, this.password, this.accountType, this.accountName)