package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.entity.Email
import com.noubug.app.xorvey.domain.gateway.EmailGateway
import com.noubug.app.xorvey.domain.gateway.LocaleGateway
import com.noubug.app.xorvey.domain.gateway.AccessGateway
import com.noubug.app.xorvey.usecase.UserForgetsPassword
import com.noubug.app.xorvey.usecase.utils.FreemarkerUtils
import java.util.*
import javax.inject.Named

@Named
class UserForgetsPasswordImpl(
        private val accessGateway: AccessGateway,
        private val freemarkerUtils: FreemarkerUtils,
        private val emailGateway: EmailGateway,
        private val localeGateway: LocaleGateway
) : UserForgetsPassword {
    override fun sendResetLink(email: String) {
        val token = accessGateway.generatePasswordResetToken(email)

        val context = HashMap<String, Any>()

        context["name"] = token.name
        context["title"] = localeGateway.getMessage("password.forgot.email.confirmation.title")
        context["message"] = localeGateway.getMessage("password.forgot.email.confirmation.message")
        context["link"] = String.format(Locale.ENGLISH, "https://www.xorvery.com/web/verify/email/password/reset?token=%s", token.token)
        context["linkText"] = localeGateway.getMessage("password.forgot.email.confirmation.link.text")

        val emailBody = freemarkerUtils.processTemplate("call-to-action-etmpl.html", context)

        emailGateway.send(Email(localeGateway.getMessage("password.forgot.email.confirmation.subject"), emailBody, true, "Xorvey <noreply@xorvey.com>", arrayOf(email), null, null, null))
    }
}