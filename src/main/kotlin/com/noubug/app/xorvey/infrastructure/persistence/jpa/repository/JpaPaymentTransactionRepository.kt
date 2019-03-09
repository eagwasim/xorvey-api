package com.noubug.app.xorvey.infrastructure.persistence.jpa.repository

import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaPaymentTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentTransactionRepository : JpaRepository<JpaPaymentTransaction, Long>