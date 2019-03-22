package com.noubug.app.xorvey.usecase

interface AccessLogin {
    fun login(email: String, password: String): String
}