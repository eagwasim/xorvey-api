package com.noubug.app.xorvey.domain.account.gateway

import com.noubug.app.xorvey.domain.account.entity.User

interface UserGateway{
    fun registerUser(user: User): Long
}