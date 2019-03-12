package com.noubug.app.xorvey.infrastructure.persistence.jpa.repository

import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaUserRepository : JpaRepository<JpaUser, Long> {
    fun findByEmail(email: String): Optional<JpaUser>
}