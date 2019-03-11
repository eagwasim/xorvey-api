package com.noubug.app.xorvey.domain.gateway

import com.noubug.app.xorvey.domain.entity.Email

interface EmailGateway {
    fun send(email: Email)
}