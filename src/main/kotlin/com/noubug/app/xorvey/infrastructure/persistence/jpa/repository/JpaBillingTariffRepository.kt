package com.noubug.app.xorvey.infrastructure.persistence.jpa.repository

import com.noubug.app.xorvey.infrastructure.persistence.jpa.model.JpaBillingAccount
import org.springframework.data.jpa.repository.JpaRepository

interface JpaBillingTariffRepository : JpaRepository<JpaBillingAccount, Long>