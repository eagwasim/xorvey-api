package com.noubug.app.xorvey.domain.gateway

interface LocaleGateway {
    fun getMessage(key: String): String
    fun getMessage(key: String, args: Array<Any>): String
}