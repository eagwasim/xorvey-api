package com.noubug.app.xorvey.usecase

interface AccessResetPassword {
    fun resetPassword(token: String, password: String)
}