package com.noubug.app.xorvey.infrastructure.gateway

import com.noubug.app.xorvey.domain.entity.Email
import com.noubug.app.xorvey.domain.gateway.EmailGateway
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import javax.inject.Named

@Named
class EmailGatewayImpl(private val javaMailSender: JavaMailSender) : EmailGateway {
    override fun send(email: Email) {
        val mail = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mail, true)

        helper.setFrom(email.from)

        email.to.forEach { t -> helper.addTo(t) }

        helper.setSubject(email.subject)
        helper.setText(email.message, email.html)

        if (email.cc != null) email.cc.forEach { c -> helper.addCc(c) }
        if (email.bcc != null) email.bcc.forEach { bcc -> helper.addBcc(bcc) }

        GlobalScope.launch {
            javaMailSender.send(mail)
        }

    }
}