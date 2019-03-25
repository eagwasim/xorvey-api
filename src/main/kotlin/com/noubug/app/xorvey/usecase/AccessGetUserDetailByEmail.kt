package com.noubug.app.xorvey.usecase

import com.noubug.app.xorvey.usecase.model.AccessUserDetail

interface AccessGetUserDetailByEmail {
    fun getUserDetails(email: String): AccessUserDetail
}