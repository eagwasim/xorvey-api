package com.noubug.app.xorvey.infrastructure.persistence.mongo.repository

import com.noubug.app.xorvey.infrastructure.persistence.mongo.model.MongoEmailVerification
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoEmailVerificationRepository : MongoRepository<MongoEmailVerification, ObjectId>