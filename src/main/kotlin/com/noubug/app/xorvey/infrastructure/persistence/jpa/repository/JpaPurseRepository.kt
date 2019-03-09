package com.noubug.app.xorvey.infrastructure.persistence.jpa.repository

import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaPurse
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPurseRepository : JpaRepository<JpaPurse, Long>