package com.noubug.app.xorvey.infrastructure.persistence.jpa.repository

import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaUser
import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserRepository : JpaRepository<JpaUser, Long>