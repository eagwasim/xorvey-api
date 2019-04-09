package com.noubug.app.xorvey.usecase

import com.noubug.app.xorvey.usecase.model.AccessUserDetail

interface GetUserDetailByEmail {
    fun getUserDetails(email: String): AccessUserDetail
}