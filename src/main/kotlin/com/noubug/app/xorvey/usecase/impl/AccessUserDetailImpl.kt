package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.AccessGateway
import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.usecase.AccessGetUserDetailByEmail
import com.noubug.app.xorvey.usecase.model.AccessUserDetail
import javax.inject.Named

@Named
class AccessUserDetailImpl(private val userGateway: UserGateway) : AccessGetUserDetailByEmail {
    override fun getUserDetails(email: String): AccessUserDetail {
        val accessGatewayUserDetail = userGateway.getUserDetailByEmail(email)
        return AccessUserDetail(accessGatewayUserDetail.userId, accessGatewayUserDetail.firstName, accessGatewayUserDetail.lastName, accessGatewayUserDetail.email, accessGatewayUserDetail.authKey, accessGatewayUserDetail.accountName, accessGatewayUserDetail.accountId)
    }
}