package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.entity.Email
import com.noubug.app.xorvey.domain.gateway.EmailGateway
import com.noubug.app.xorvey.domain.gateway.LocaleGateway
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
        private val freemarkerUtils: FreemarkerUtils,
        private val localeGateway: LocaleGateway
) : UserRegistration {
    override fun registerUser(userRegistrationRequest: UserRegistrationRequest): UserRegistrationResponse {
        val registeredUser = userGateway.registerUser(userRegistrationRequest.toUser())

        val context = HashMap<String, Any>()

        context["name"] = userRegistrationRequest.firstName
        context["title"] = localeGateway.getMessage("registration.email.confirmation.title")
        context["message"] = localeGateway.getMessage("registration.email.confirmation.message", arrayOf(userRegistrationRequest.accountType.replace("_", " ")))
        context["link"] = String.format(Locale.ENGLISH, "https://www.xorvery.com/web/verify/email/reg?token=%s", registeredUser.emailVerificationToken)
        context["linkText"] = localeGateway.getMessage("registration.email.confirmation.link.text")

        val emailBody = freemarkerUtils.processTemplate("call-to-action-etmpl.html", context)

        val email = Email(localeGateway.getMessage("registration.email.confirmation.subject"), emailBody, true, "Xorvey <noreply@xorvey.com>", arrayOf(userRegistrationRequest.email), null, null, null)
        emailGateway.send(email)

        return UserRegistrationResponse(registeredUser.userId)
    }
}