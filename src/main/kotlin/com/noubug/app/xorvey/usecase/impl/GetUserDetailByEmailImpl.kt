package com.noubug.app.xorvey.usecase.impl

import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.usecase.GetUserDetailByEmail
import com.noubug.app.xorvey.usecase.model.AccessUserDetail
import javax.inject.Named

@Named
class GetUserDetailByEmailImpl(private val userGateway: UserGateway) : GetUserDetailByEmail {
    override fun getUserDetails(email: String): AccessUserDetail {
        val accessGatewayUserDetail = userGateway.getUserDetailByEmail(email)
        return AccessUserDetail(accessGatewayUserDetail.userId, accessGatewayUserDetail.firstName, accessGatewayUserDetail.lastName, accessGatewayUserDetail.email, accessGatewayUserDetail.authKey, accessGatewayUserDetail.accountName, accessGatewayUserDetail.accountId)
    }
}