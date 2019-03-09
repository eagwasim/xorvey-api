package com.noubug.app.xorvey.infrastructure.persistence.jpa.repository

import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaAccount
import org.springframework.data.jpa.repository.JpaRepository

interface JpaAccountRepository : JpaRepository<JpaAccount, Long>