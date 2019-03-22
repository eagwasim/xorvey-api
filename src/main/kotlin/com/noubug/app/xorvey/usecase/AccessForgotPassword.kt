package com.noubug.app.xorvey.usecase

interface AccessForgotPassword {
    fun sendResetLink(email: String)
}