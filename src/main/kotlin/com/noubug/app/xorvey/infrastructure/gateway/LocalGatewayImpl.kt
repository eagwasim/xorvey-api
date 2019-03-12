package com.noubug.app.xorvey.infrastructure.gateway

import com.noubug.app.xorvey.domain.gateway.LocaleGateway
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import javax.inject.Named

@Named
class LocalGatewayImpl(private val messageSource: MessageSource) : LocaleGateway {
    override fun getMessage(key: String): String {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale())
    }

    override fun getMessage(key: String, args: Array<Any>): String {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale())
    }
}