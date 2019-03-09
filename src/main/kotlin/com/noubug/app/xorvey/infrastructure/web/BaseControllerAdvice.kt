package com.noubug.app.xorvey.infrastructure.web

import com.noubug.app.xorvey.infrastructure.web.model.ControllerResponseJSON
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception

@ControllerAdvice
class BaseControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ControllerResponseJSON<Nothing>> {
        exception.printStackTrace()
        return ResponseEntity(ControllerResponseJSON("An internal server error occurred. We are aware of this and looking into it", null), HttpStatus.INTERNAL_SERVER_ERROR)
    }

}