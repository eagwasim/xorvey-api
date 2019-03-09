package com.noubug.app.xorvey.infrastructure.web.model

data class ControllerResponseJSON<T>(val message: String, val data: T?)