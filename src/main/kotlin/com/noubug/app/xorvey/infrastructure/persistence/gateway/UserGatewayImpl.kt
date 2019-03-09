package com.noubug.app.xorvey.infrastructure.persistence.gateway

import com.noubug.app.xorvey.domain.account.entity.User
import com.noubug.app.xorvey.domain.account.gateway.UserGateWay
import com.noubug.app.xorvey.infrastructure.persistence.jpa.repository.JpaUserRepository
import javax.inject.Named

@Named
class UserGatewayImpl(val jpaUserRepository: JpaUserRepository) : UserGateWay {
    override fun registerUser(user: User) {
      
    }
}