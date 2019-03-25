package com.noubug.app.xorvey.infrastructure.gateway

import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.domain.model.UserGatewayUserDetail
import com.noubug.app.xorvey.infrastructure.exception.InputParameterException
import com.noubug.app.xorvey.infrastructure.persistence.jpa.repository.JpaUserRepository
import org.springframework.context.MessageSource
import java.util.*
import javax.inject.Named

@Named
class UserGatewayImpl(private val jpaUserRepository: JpaUserRepository, private val messageSource: MessageSource) : UserGateway {
    override fun getUserDetailByEmail(email: String): UserGatewayUserDetail {
        val optionalJpaUser = jpaUserRepository.findByEmail(email)

        if (!optionalJpaUser.isPresent) {
            throw InputParameterException(messageSource.getMessage("input.error.email_or_password", null, Locale.getDefault()))
        }

        val jpaUser = optionalJpaUser.get()

        return UserGatewayUserDetail(jpaUser.getId()!!, jpaUser.firstName, jpaUser.lastName, jpaUser.email, jpaUser.authKey, jpaUser.account.name, jpaUser.account.getId()!!)
    }
}