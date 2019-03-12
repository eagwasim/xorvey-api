package com.noubug.app.xorvey.usecase

interface RegistrationEmailConfirmation {
    fun confirmEmailToken(token: String): Boolean
}