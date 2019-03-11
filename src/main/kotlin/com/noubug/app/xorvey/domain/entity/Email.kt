package com.noubug.app.xorvey.domain.entity

import java.io.File

class Email(val subject: String, val message: String, val html: Boolean, val from: String, val to: Array<String>, val cc: Array<String>?, val bcc: Array<String>?, val attachments: Array<File>?)