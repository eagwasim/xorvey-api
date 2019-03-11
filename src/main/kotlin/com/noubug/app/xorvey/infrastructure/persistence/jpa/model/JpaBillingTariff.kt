package com.noubug.app.xorvey.infrastructure.persistence.jpa.model

import com.noubug.app.xorvey.infrastructure.persistence.enumeration.TariffConstant
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "billing_tariff")
data class JpaBillingTariff(
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        val tariffName: TariffConstant,
        @Column(nullable = false)
        var amount: BigDecimal
) : JpaAbstractPersistable<Long>()