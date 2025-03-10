package com.luccazx12.health.checkers

import com.luccazx12.health.HealthChecker
import com.mongodb.client.MongoClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class MongoDBHealthChecker(private val mongoClient: MongoClient) : HealthChecker {

    override suspend fun isHealthy(): Boolean {
        return try {
            withContext(Dispatchers.IO) { mongoClient.listDatabaseNames().iterator().hasNext() }
        } catch (e: Exception) {
            false
        }
    }

    override fun getName(): String {
        return "mongodb"
    }
}
