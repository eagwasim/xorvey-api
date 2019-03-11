package com.noubug.app.xorvey.infrastructure.persistence.jpa.model

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "billing_account")
data class JpaBillingAccount(
        @Column(nullable = false)
        var creditBalance: BigDecimal,
        @OneToOne(optional = false)
        val account: JpaAccount
) : JpaAbstractPersistable<Long>()