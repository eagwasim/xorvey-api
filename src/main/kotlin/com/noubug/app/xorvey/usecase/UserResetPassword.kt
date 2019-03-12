package com.noubug.app.xorvey.usecase

interface UserResetPassword {
    fun resetPassword(token: String, password: String)
}