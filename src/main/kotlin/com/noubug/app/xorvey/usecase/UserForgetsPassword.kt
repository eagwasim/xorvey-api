package com.noubug.app.xorvey.usecase

interface UserForgetsPassword {
    fun sendResetLink(email: String)
}