package com.noubug.app.xorvey.infrastructure.gateway

import com.noubug.app.xorvey.domain.entity.Account
import com.noubug.app.xorvey.domain.entity.Access
import com.noubug.app.xorvey.domain.gateway.AccessGateway
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
import java.util.*
import javax.inject.Named
import javax.transaction.Transactional

@Named
open class AccessGatewayImpl(
        private val jpaUserRepository: JpaUserRepository,
        private val jpaAccountRepository: JpaAccountRepository,
        private val jpaPurseRepository: JpaPurseRepository,
        private val jpaBillingAccountRepository: JpaBillingAccountRepository,
        private val mongoEmailVerificationRepository: MongoEmailVerificationRepository,
        private val mongoPasswordResetEmailVerificationRepository: MongoPasswordResetEmailVerificationRepository,
        private val jwtTokenProvider: JwtTokenProvider,
        private val messageSource: MessageSource
) : AccessGateway {

    override fun authenticateUser(authenticateUserRequest: AuthenticateUserRequest): AuthenticateUserResponse {
        val optionalJpaUser = jpaUserRepository.findByEmail(authenticateUserRequest.email)

        if (!optionalJpaUser.isPresent) {
            throw InputParameterException(messageSource.getMessage("input.error.email_or_password", null, Locale.getDefault()))
        }

        val jpaUser = optionalJpaUser.get()

        if (!BCrypt.checkpw(authenticateUserRequest.password, jpaUser.password)) {
            throw InputParameterException(messageSource.getMessage("input.error.email_or_password", null, Locale.getDefault()))
        }

        if (!jpaUser.emailVerified) {
            throw InputParameterException(messageSource.getMessage("input.error.email_not_verified", null, Locale.getDefault()))
        }

        val token = jwtTokenProvider.createToken(jpaUser.email, jpaUser.authKey)

        return AuthenticateUserResponse(Access(jpaUser.getId(), jpaUser.firstName, jpaUser.lastName, jpaUser.email, jpaUser.authKey, jpaUser.emailVerified, jpaUser.password, Account(jpaUser.account.name, jpaUser.account.type.name)), token)
    }

    @Transactional
    override fun updateUserPasswordByToken(token: String, password: String) {
        val optionalMongoPasswordResetEmailVerification = mongoPasswordResetEmailVerificationRepository.findById(ObjectId(token))

        if (!optionalMongoPasswordResetEmailVerification.isPresent) {
            throw InputParameterException(messageSource.getMessage("input.error.invalid_token", null, Locale.getDefault()))
        }

        val optionalJpaUser = jpaUserRepository.findByEmail(optionalMongoPasswordResetEmailVerification.get().email)

        val jpaUser = optionalJpaUser.get()

        jpaUser.password = BCrypt.hashpw(password, BCrypt.gensalt())

        jpaUserRepository.save(jpaUser)

        mongoPasswordResetEmailVerificationRepository.delete(optionalMongoPasswordResetEmailVerification.get())
    }

    override fun generatePasswordResetToken(email: String): PasswordResetToken {
        val tempUser = jpaUserRepository.findByEmail(email.toLowerCase())

        if (!tempUser.isPresent) {
            throw InputParameterException(messageSource.getMessage("input.error.email_not_registered", null, Locale.getDefault()))
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
    override fun registerUser(access: Access): RegisteredUser {
        val tempUser = jpaUserRepository.findByEmail(access.email.toLowerCase())

        if (tempUser.isPresent) {
            throw InputParameterException(messageSource.getMessage("input.error.email_already_registered", null, Locale.getDefault()))
        }

        val jpaUserAccount = JpaAccount(access.account.name, AccountTypeConstant.valueOf(access.account.type))
        jpaAccountRepository.save(jpaUserAccount)

        val jpaBillingAccount = JpaBillingAccount(BigDecimal(20), jpaUserAccount)
        jpaBillingAccountRepository.save(jpaBillingAccount)

        val jpaUser = JpaUser(WordUtils.capitalizeFully(access.firstName.toLowerCase()), WordUtils.capitalizeFully(access.lastName.toLowerCase()), access.email.toLowerCase().trim(), RandomStringUtils.randomAlphanumeric(6), false, BCrypt.hashpw(access.password, BCrypt.gensalt()), jpaUserAccount)
        jpaUserRepository.save(jpaUser)

        val jpaPurse = JpaPurse(jpaUser, 0L)
        jpaPurseRepository.save(jpaPurse)

        val mongoEmailVerification = MongoEmailVerification(jpaUser.email)
        mongoEmailVerificationRepository.save(mongoEmailVerification)

        return RegisteredUser(jpaUser.getId()!!, mongoEmailVerification.getId()!!.toHexString())
    }
}