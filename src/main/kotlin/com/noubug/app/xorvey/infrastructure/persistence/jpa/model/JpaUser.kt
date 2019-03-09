package com.noubug.app.xorvey.infrastructure.persistence.jpa.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "app_user")
data class JpaUser(
        @Column(nullable = false)
        var firstName: String,
        @Column(nullable = false)
        var lastName: String,
        @Column(nullable = false)
        var email: String,
        @Column(nullable = false)
        var authKey: String,
        @Column(nullable = false)
        var emailVerified: String,
        @Column(nullable = false)
        @ManyToOne
        val account: JpaAccount
) : JpaAbstractPersistable<Long>()