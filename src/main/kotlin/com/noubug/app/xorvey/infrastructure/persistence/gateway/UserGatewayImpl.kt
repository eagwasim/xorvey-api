package com.noubug.app.xorvey.infrastructure.persistence.gateway

import com.noubug.app.xorvey.domain.entity.User
import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.domain.model.RegisteredUser
import com.noubug.app.xorvey.infrastructure.exception.InputParameterException
import com.noubug.app.xorvey.infrastructure.persistence.enumeration.AccountTypeConstant
import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaAccount
import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaBillingAccount
import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaPurse
import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaUser
import com.noubug.app.xorvey.infrastructure.persistence.jpa.repository.JpaAccountRepository
import com.noubug.app.xorvey.infrastructure.persistence.jpa.repository.JpaBillingAccountRepository
import com.noubug.app.xorvey.infrastructure.persistence.jpa.repository.JpaPurseRepository
import com.noubug.app.xorvey.infrastructure.persistence.jpa.repository.JpaUserRepository
import com.noubug.app.xorvey.infrastructure.persistence.mongo.model.MongoEmailVerification
import com.noubug.app.xorvey.infrastructure.persistence.mongo.repository.MongoEmailVerificationRepository
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.text.WordUtils
import org.springframework.security.crypto.bcrypt.BCrypt
import java.math.BigDecimal
import javax.inject.Named
import javax.transaction.Transactional

@Named
open class UserGatewayImpl(
        private val jpaUserRepository: JpaUserRepository,
        private val jpaAccountRepository: JpaAccountRepository,
        private val jpaPurseRepository: JpaPurseRepository,
        private val jpaBillingAccountRepository: JpaBillingAccountRepository,
        private val mongoEmailVerificationRepository: MongoEmailVerificationRepository
) : UserGateway {


    @Override
    @Transactional
    override fun registerUser(user: User): RegisteredUser {
        val tempUser = jpaUserRepository.findByEmail(user.email.toLowerCase())

        if (tempUser.isPresent) {
            throw InputParameterException("This email address has already been registered")
        }

        val jpaUserAccount = JpaAccount(user.account.name, AccountTypeConstant.valueOf(user.account.type))
        jpaAccountRepository.save(jpaUserAccount)

        val jpaBillingAccount = JpaBillingAccount(BigDecimal(20), jpaUserAccount)
        jpaBillingAccountRepository.save(jpaBillingAccount)

        val jpaUser = JpaUser(WordUtils.capitalizeFully(user.firstName.toLowerCase()), WordUtils.capitalizeFully(user.lastName.toLowerCase()), user.email.toLowerCase().trim(), RandomStringUtils.randomAlphanumeric(6), false, BCrypt.hashpw(user.password, BCrypt.gensalt()), jpaUserAccount)
        jpaUserRepository.save(jpaUser)

        val jpaPurse = JpaPurse(jpaUser, 0L)
        jpaPurseRepository.save(jpaPurse)

        val mongoEmailVerification = MongoEmailVerification(jpaUser.email)
        mongoEmailVerificationRepository.save(mongoEmailVerification)

        return RegisteredUser(jpaUser.getId()!!, mongoEmailVerification.getId()!!.toHexString())
    }
}