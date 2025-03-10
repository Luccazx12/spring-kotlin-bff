package com.luccazx12.health.checkers

import com.luccazx12.health.HealthChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.stereotype.Component

@Component
class RedisHealthChecker(private val redisConnectionFactory: RedisConnectionFactory) :
        HealthChecker {

  override suspend fun isHealthy(): Boolean {
    return withContext(Dispatchers.IO) {
      try {
        redisConnectionFactory.connection.use { connection -> connection.ping() != null }
      } catch (e: Exception) {
        false
      }
    }
  }

  override fun getName(): String {
    return "redis"
  }
}
