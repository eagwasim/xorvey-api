package com.noubug.app.xorvey.infrastructure.web.controllers

import com.noubug.app.xorvey.infrastructure.web.model.*
import com.noubug.app.xorvey.usecase.*
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import java.util.*
import javax.validation.Valid

@RestController
class AccessController(
        val userLogin: UserLogin,
        val userForgotPassword: UserForgotPassword,
        val userRegistration: UserRegistration,
        val userResetPassword: UserResetPassword,
        val emailConfirmation: RegistrationEmailConfirmation,
        val passwordResetEmailConfirmation: PasswordResetEmailConfirmation
) {

    @ResponseBody
    @PostMapping("/api/v1/access/register")
    fun register(@Valid @RequestBody userRegistrationRequestJSON: UserRegistrationRequestJSON): ControllerResponseJSON<UserRegistrationResponseJSON> {
        return ControllerResponseJSON("success", UserRegistrationResponseJSON(userRegistration.registerUser(userRegistrationRequestJSON.toRequest()).userId))
    }

    @ResponseBody
    @PostMapping("/api/v1/access/login")
    fun login(@RequestBody @Valid userLoginRequestJSON: UserLoginRequestJSON): ControllerResponseJSON<UserLoginResponseJSON> {
        return ControllerResponseJSON("Success", UserLoginResponseJSON(userLogin.login(userLoginRequestJSON.email, userLoginRequestJSON.password)))
    }

    @ResponseBody
    @PostMapping("/api/v1/access/password/forgot")
    fun forgotPassword(@RequestBody @Valid userForgotPasswordRequestJSON: UserForgotPasswordRequestJSON): ControllerResponseJSON<Nothing> {
        userForgotPassword.sendResetLink(userForgotPasswordRequestJSON.email)
        return ControllerResponseJSON("Reset Link Sent", null)
    }

    @ResponseBody
    @PostMapping("/api/v1/access/password/reset")
    fun passwordReset(@RequestBody @Valid userResetPasswordRequestJSON: UserResetPasswordRequestJSON): ControllerResponseJSON<Nothing> {
        userResetPassword.resetPassword(userResetPasswordRequestJSON.token, userResetPasswordRequestJSON.password)
        return ControllerResponseJSON("Password reset successfully", null)
    }

    @GetMapping("/web/verify/email/reg")
    fun emailConfirmationForRegistration(@Param("token") token: String): RedirectView {
        val confirmed = emailConfirmation.confirmEmailToken(token)
        return RedirectView(String.format(Locale.ENGLISH, "/auth/login?confirmed=%s", confirmed))
    }

    @GetMapping("/web/verify/email/password/reset")
    fun emailConfirmationForPasswordReset(@Param("token") token: String): RedirectView {
        val confirmed = passwordResetEmailConfirmation.confirmEmailToken(token)

        if (confirmed) {
            return RedirectView(String.format(Locale.ENGLISH, "/auth/password/reset?token=%s", token))
        }

        return RedirectView(String.format(Locale.ENGLISH, "/auth/login?confirmed=%s", confirmed))
    }
}