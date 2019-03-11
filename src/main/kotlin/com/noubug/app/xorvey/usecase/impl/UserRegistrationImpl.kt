package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.entity.Email
import com.noubug.app.xorvey.domain.gateway.EmailGateway
import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.usecase.UserRegistration
import com.noubug.app.xorvey.usecase.model.UserRegistrationRequest
import com.noubug.app.xorvey.usecase.model.UserRegistrationResponse
import com.noubug.app.xorvey.usecase.model.toUser
import com.noubug.app.xorvey.usecase.utils.FreemarkerUtils
import java.util.*
import javax.inject.Named

@Named
class UserRegistrationImpl(
        private val userGateway: UserGateway,
        private val emailGateway: EmailGateway,
        private val freemarkerUtils: FreemarkerUtils
) : UserRegistration {
    override fun registerUser(userRegistrationRequest: UserRegistrationRequest): UserRegistrationResponse {
        val registeredUser = userGateway.registerUser(userRegistrationRequest.toUser())

        val context = HashMap<String, Any>()

        context["name"] = userRegistrationRequest.firstName
        context["title"] = "Xorvey Email Confirmation"
        context["message"] = String.format(Locale.ENGLISH, "You have created a Xorvey account. As an extra security measure, please verify this is the correct email address by clicking the button below")
        context["link"] = String.format(Locale.ENGLISH, "https://www.xorvery.com/web/verify/email/reg?token=%s", registeredUser.emailVerificationToken)
        context["linkText"] = "Confirm Your Email With Xorvey"

        val emailBody = freemarkerUtils.processTemplate("call-to-action-etmpl.html", context)

        val email = Email("Please confirm the email used to register on Xorvey", emailBody, true, "Xorvey <noreply@xorvey.com>", arrayOf(userRegistrationRequest.email), null, null, null)
        emailGateway.send(email)

        return UserRegistrationResponse(registeredUser.userId)
    }
}