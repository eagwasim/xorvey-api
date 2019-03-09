package com.noubug.app.xorvey.domain.account.entity

data class User(
        val id: Long?,
        val firstName: String,
        val lastName: String,
        val email: String,
        val authKey: String?,
        val emailVerified: Boolean,
        val password: String
)