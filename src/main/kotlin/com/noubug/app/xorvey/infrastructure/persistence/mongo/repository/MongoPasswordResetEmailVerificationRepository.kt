package com.noubug.app.xorvey.infrastructure.persistence.mongo.repository

import com.noubug.app.xorvey.infrastructure.persistence.mongo.model.MongoPasswordResetEmailVerification
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoPasswordResetEmailVerificationRepository : MongoRepository<MongoPasswordResetEmailVerification, ObjectId>