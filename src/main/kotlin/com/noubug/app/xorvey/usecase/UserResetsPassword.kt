package com.noubug.app.xorvey.usecase

interface UserResetsPassword {
    fun resetPassword(token: String, password: String)
}