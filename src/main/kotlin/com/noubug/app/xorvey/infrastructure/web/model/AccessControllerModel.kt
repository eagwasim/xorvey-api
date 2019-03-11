package com.noubug.app.xorvey.infrastructure.web.model

import com.noubug.app.xorvey.usecase.model.UserRegistrationRequest
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class UserRegistrationRequestJSON(
        @field:NotNull
        @field:NotBlank
        val firstName: String,
        @field:NotNull
        @field:NotBlank
        val lastName: String,
        @field:NotNull
        @field:NotBlank
        @field:Email
        val email: String,
        @field:NotNull
        @field:NotBlank
        val password: String,
        @field:NotNull
        @field:NotBlank
        @field:Pattern(regexp = "(SURVEY_TAKER|SURVEY_PUBLISHER)")
        val accountType: String,
        @field:NotNull
        @field:NotBlank
        val accountName: String
)

data class UserRegistrationResponseJSON(val userId: Long)
class UserLoginRequestJSON()
class UserLoginResponseJSON()
class UserForgotPasswordRequestJSON()
class UserForgotPasswordResponseJSON()
class UserResetPasswordRequestJSON()
class UserResetPasswordResponseJSON()

fun UserRegistrationRequestJSON.toRequest() = UserRegistrationRequest(this.firstName, this.lastName, this.email, this.password, this.accountType, this.accountName)