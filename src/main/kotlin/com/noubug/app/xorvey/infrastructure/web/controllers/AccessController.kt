package com.noubug.app.xorvey.infrastructure.web.controllers

import com.noubug.app.xorvey.infrastructure.web.model.*
import com.noubug.app.xorvey.usecase.UserForgotPassword
import com.noubug.app.xorvey.usecase.UserLogin
import com.noubug.app.xorvey.usecase.UserRegistration
import com.noubug.app.xorvey.usecase.UserResetPassword
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AccessController(val userLogin: UserLogin, val userForgotPassword: UserForgotPassword, val userRegistration: UserRegistration, val userResetPassword: UserResetPassword) {

    @ResponseBody
    @PostMapping("/api/v1/access/register")
    fun register(@RequestBody @Valid userRegistrationRequestJSON: UserRegistrationRequestJSON): ControllerResponseJSON<UserRegistrationResponseJSON> {
        return ControllerResponseJSON("success", UserRegistrationResponseJSON(userRegistration.registerUser(userRegistrationRequestJSON.toRequest()).userId))
    }

    @ResponseBody
    @PostMapping("/api/v1/access/login")
    fun login(@RequestBody @Valid userLoginRequestJSON: UserLoginRequestJSON): ControllerResponseJSON<UserLoginResponseJSON> {
        return ControllerResponseJSON("", UserLoginResponseJSON())
    }

    @ResponseBody
    @PostMapping("/api/v1/access/password/forgot")
    fun forgotPassword(@RequestBody @Valid userForgotPasswordRequestJSON: UserForgotPasswordRequestJSON): ControllerResponseJSON<UserForgotPasswordResponseJSON> {
        return ControllerResponseJSON("", UserForgotPasswordResponseJSON())
    }

    @ResponseBody
    @PostMapping("/api/v1/access/password/reset")
    fun passwordReset(@RequestBody @Valid userResetPasswordRequestJSON: UserResetPasswordRequestJSON): ControllerResponseJSON<UserResetPasswordResponseJSON> {
        return ControllerResponseJSON("", UserResetPasswordResponseJSON())
    }
}