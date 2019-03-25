package com.noubug.app.xorvey.usecase.model

data class AccessUserDetail(
        val userId: Long,
        val firstName: String,
        val lastName: String,
        val email: String,
        val authKey: String,
        val accountName: String,
        val accountId: Long
)