package com.noubug.app.xorvey.infrastructure.persistence.jpa.model

import javax.persistence.*

@Entity
@Table(name = "purse")
data class JpaPurse(
        @OneToOne(fetch = FetchType.EAGER, optional = false)
        val user: JpaUser,
        @Column(nullable = false) val balance: Long
) : JpaAbstractPersistable<Long>()