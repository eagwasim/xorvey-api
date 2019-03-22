package com.noubug.app.xorvey.domain.entity

data class Access(
        val id: Long?,
        val firstName: String,
        val lastName: String,
        val email: String,
        val authKey: String?,
        val emailVerified: Boolean,
        val password: String,
        val account: Account
)