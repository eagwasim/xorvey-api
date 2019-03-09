package com.noubug.app.xorvey.infrastructure.persistence.jpa.model

import com.noubug.app.xorvey.infrastructure.persistence.enumeration.AccountTypeConstant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "account")
data class JpaAccount(@Column(nullable = false) var name: String, @Column(nullable = false) val type: AccountTypeConstant) : JpaAbstractPersistable<Long>()