package com.noubug.app.xorvey.infrastructure.gateway

import com.noubug.app.xorvey.domain.entity.Account
import com.noubug.app.xorvey.domain.entity.User
import com.noubug.app.xorvey.domain.gateway.UserGateway
import com.noubug.app.xorvey.domain.model.AuthenticateUserRequest
import com.noubug.app.xorvey.domain.model.AuthenticateUserResponse
import com.noubug.app.xorvey.domain.model.PasswordResetToken
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
import com.noubug.app.xorvey.infrastructure.persistence.mongo.model.MongoPasswordResetEmailVerification
import com.noubug.app.xorvey.infrastructure.persistence.mongo.repository.MongoEmailVerificationRepository
import com.noubug.app.xorvey.infrastructure.persistence.mongo.repository.MongoPasswordResetEmailVerificationRepository
import com.noubug.app.xorvey.infrastructure.web.security.JwtTokenProvider
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.text.WordUtils
import org.bson.types.ObjectId
import org.springframework.context.MessageSource
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
        private val mongoEmailVerificationRepository: MongoEmailVerificationRepository,
        private val mongoPasswordResetEmailVerificationRepository: MongoPasswordResetEmailVerificationRepository,
        private val jwtTokenProvider: JwtTokenProvider,
        private val messageSource: MessageSource
) : UserGateway {
    override fun authenticateUser(authenticateUserRequest: AuthenticateUserRequest): AuthenticateUserResponse {
        val optionalJpaUser = jpaUserRepository.findByEmail(authenticateUserRequest.email)

        if (!optionalJpaUser.isPresent) {
            throw InputParameterException("Email or password incorrect")
        }

        val jpaUser = optionalJpaUser.get()

        if (!BCrypt.checkpw(authenticateUserRequest.password, jpaUser.password)) {
            throw InputParameterException("Email or password incorrect")
        }

        if (!jpaUser.emailVerified) {
            throw InputParameterException("Email not verified")
        }

        val token = jwtTokenProvider.createToken(jpaUser.email, jpaUser.authKey)

        return AuthenticateUserResponse(User(jpaUser.getId(), jpaUser.firstName, jpaUser.lastName, jpaUser.email, jpaUser.authKey, jpaUser.emailVerified, jpaUser.password, Account(jpaUser.account.name, jpaUser.account.type.name)), token)
    }

    override fun updateUserPasswordByToken(token: String, password: String) {

    }

    override fun generatePasswordResetToken(email: String): PasswordResetToken {
        val tempUser = jpaUserRepository.findByEmail(email.toLowerCase())

        if (!tempUser.isPresent) {
            throw InputParameterException("This email address is not registered")
        }

        val mongoPasswordResetEmailVerification = MongoPasswordResetEmailVerification(email)
        mongoPasswordResetEmailVerificationRepository.save(mongoPasswordResetEmailVerification)

        return PasswordResetToken(tempUser.get().firstName, mongoPasswordResetEmailVerification.getId()!!.toHexString())
    }

    override fun confirmPasswordResetEmail(token: String): Boolean {
        val optionalMongoEmailVerification = mongoPasswordResetEmailVerificationRepository.findById(ObjectId(token))
        return optionalMongoEmailVerification.isPresent
    }

    @Transactional
    override fun confirmRegistrationEmail(token: String): Boolean {
        val optionalMongoEmailVerification = mongoEmailVerificationRepository.findById(ObjectId(token))

        if (!optionalMongoEmailVerification.isPresent) {
            return false
        }

        val userEmail = optionalMongoEmailVerification.get().email
        val jpaUser = jpaUserRepository.findByEmail(userEmail.toLowerCase()).get()

        jpaUser.emailVerified = true

        jpaUserRepository.save(jpaUser)

        mongoEmailVerificationRepository.delete(optionalMongoEmailVerification.get())

        return true
    }

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