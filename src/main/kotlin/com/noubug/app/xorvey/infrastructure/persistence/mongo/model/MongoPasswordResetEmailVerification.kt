package com.noubug.app.xorvey.infrastructure.persistence.mongo.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document("password_reset_email_verification")
data class MongoPasswordResetEmailVerification(val email: String) : MongoAbstractPersistable<ObjectId>()