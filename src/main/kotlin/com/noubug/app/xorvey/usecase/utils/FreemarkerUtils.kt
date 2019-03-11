package com.noubug.app.xorvey.usecase.utils

import freemarker.template.Configuration
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import javax.inject.Named

@Named
class FreemarkerUtils(private val freemarkerConfig: Configuration) {

    fun processTemplate(template: String, context: Map<String, Any>): String {
        val t = freemarkerConfig.getTemplate(template)
        return FreeMarkerTemplateUtils.processTemplateIntoString(t, context)
    }

}