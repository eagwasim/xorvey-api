package com.noubug.app.xorvey.usecase

interface PasswordResetEmailConfirmation {
    fun confirmEmailToken(token: String): Boolean
}