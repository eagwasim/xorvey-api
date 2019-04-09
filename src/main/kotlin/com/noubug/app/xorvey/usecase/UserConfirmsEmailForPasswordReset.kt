package com.noubug.app.xorvey.usecase

interface UserConfirmsEmailForPasswordReset {
    fun confirmEmailToken(token: String): Boolean
}