package com.noubug.app.xorvey.infrastructure.persistence.jpa.model

import com.noubug.app.xorvey.infrastructure.persistence.enumeration.EntityStatusConstant
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class JpaAbstractPersistable<T : Serializable> {

    companion object {
        private val serialVersionUID = -5554308939380869754L
    }

    @Id
    @GeneratedValue
    private var id: T? = null
    private var dateCreated: LocalDateTime? = null
    private var dateModified: LocalDateTime? = null

    @Enumerated(EnumType.STRING)
    var status: EntityStatusConstant = EntityStatusConstant.ACTIVE

    fun getId(): T? {
        return id
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as JpaAbstractPersistable<*>

        return if (null == this.getId()) false else this.getId() == other.getId()
    }


    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"

    fun getDateCreated(): LocalDateTime? {
        return dateCreated
    }

    fun getDateModified(): LocalDateTime? {
        return dateModified
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    @PrePersist
    fun beforeSave() {
        if (this.dateCreated == null) {
            this.dateCreated = LocalDateTime.now()
        }

        this.dateModified = LocalDateTime.now()
    }
}