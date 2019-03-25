package com.noubug.app.xorvey.domain.gateway

import com.noubug.app.xorvey.domain.model.UserGatewayUserDetail

interface UserGateway {
    fun getUserDetailByEmail(email: String): UserGatewayUserDetail
}