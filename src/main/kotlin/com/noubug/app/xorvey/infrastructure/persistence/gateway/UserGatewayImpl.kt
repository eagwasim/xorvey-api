package com.noubug.app.xorvey.infrastructure.persistence.gateway

import com.noubug.app.xorvey.domain.account.entity.User
import com.noubug.app.xorvey.domain.account.gateway.UserGateway
import com.noubug.app.xorvey.infrastructure.persistence.jpa.repository.JpaUserRepository
import javax.inject.Named

@Named
class UserGatewayImpl(val jpaUserRepository: JpaUserRepository) : UserGateway {
    override fun registerUser(user: User): Long {
        return 0L
    }
}