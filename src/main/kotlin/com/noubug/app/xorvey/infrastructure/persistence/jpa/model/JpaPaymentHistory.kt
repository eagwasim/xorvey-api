package com.noubug.app.xorvey.infrastructure.persistence.jpa.model

import com.noubug.app.xorvey.infrastructure.persistence.enumeration.PaymentStatusConstant
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "payment_history")
data class JpaPaymentHistory(
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        val billingAccount: JpaBillingAccount,
        @Column(nullable = false)
        val amount: BigDecimal,
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var paymentStatus: PaymentStatusConstant,
        @Column(nullable = false, length = 256)
        val description: String
) : JpaAbstractPersistable<Long>()