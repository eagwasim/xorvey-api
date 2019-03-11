package com.noubug.app.xorvey.infrastructure.persistence.mongo.model

import org.springframework.data.util.ProxyUtils
import java.io.Serializable

abstract class MongoAbstractPersistable<T : Serializable> {

    companion object {
        private val serialVersionUID = -5554308939380869754L
    }

    @org.springframework.data.annotation.Id
    private var id: T? = null

    fun getId(): T? {
        return id
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as MongoAbstractPersistable<*>

        return if (null == this.getId()) false else this.getId() == other.getId()
    }


    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}