package com.noubug.app.xorvey.infrastructure.persistence.jpa.model

import com.noubug.app.xorvey.infrastructure.persistence.enumeration.PaymentChannelConstant
import com.noubug.app.xorvey.infrastructure.persistence.enumeration.PaymentProviderConstant
import com.noubug.app.xorvey.infrastructure.persistence.enumeration.PaymentStatusConstant
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "payment_transaction")
data class JpaPaymentTransaction(
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        val paymentHistory: JpaPaymentHistory,
        @Column(nullable = false)
        var paymentStatus: PaymentStatusConstant,
        @Column(nullable = false)
        val paymentChannel: PaymentChannelConstant,
        @Column(nullable = false)
        val paymentProvider: PaymentProviderConstant,
        @Column(nullable = false)
        val amount: BigDecimal,
        @Column(unique = true)
        val reference: String
): JpaAbstractPersistable<Long>()