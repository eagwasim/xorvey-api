package com.noubug.app.xorvey.usecase

interface UserForgotPassword {
    fun sendResetLink(email: String)
}