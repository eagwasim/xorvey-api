package com.noubug.app.xorvey.usecase

interface UserConfirmsEmailAfterRegistration {
    fun confirmEmailToken(token: String): Boolean
}