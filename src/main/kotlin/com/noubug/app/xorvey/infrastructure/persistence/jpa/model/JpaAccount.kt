package com.noubug.app.xorvey.infrastructure.persistence.jpa.model

import com.noubug.app.xorvey.infrastructure.persistence.enumeration.AccountTypeConstant
import javax.persistence.*

@Entity
@Table(name = "account")
data class JpaAccount(
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        val type: AccountTypeConstant) : JpaAbstractPersistable<Long>() {
}