package com.noubug.app.xorvey.infrastructure.persistence.mongo.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document("email_verification")
data class MongoEmailVerification(val email: String) : MongoAbstractPersistable<ObjectId>()