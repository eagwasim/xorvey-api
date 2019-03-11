package com.noubug.app.xorvey.domain.gateway

import com.noubug.app.xorvey.domain.entity.User
import com.noubug.app.xorvey.domain.model.RegisteredUser

interface UserGateway {
    fun registerUser(user: User): RegisteredUser
}