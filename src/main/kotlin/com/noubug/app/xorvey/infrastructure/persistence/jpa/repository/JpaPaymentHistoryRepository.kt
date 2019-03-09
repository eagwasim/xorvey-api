package com.noubug.app.xorvey.infrastructure.persistence.jpa.repository

import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaPaymentHistory
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentHistoryRepository : JpaRepository<JpaPaymentHistory, Long>