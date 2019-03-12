package com.noubug.app.xorvey.usecase

interface UserLogin {
    fun login(email: String, password: String): String
}