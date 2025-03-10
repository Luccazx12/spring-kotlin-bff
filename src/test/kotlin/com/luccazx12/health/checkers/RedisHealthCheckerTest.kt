package com.luccazx12.health.checkers

import com.luccazx12.health.HealthChecker
import io.mockk.every
import io.mockk.verify
import io.mockk.mockk
import io.mockk.confirmVerified
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.redis.connection.RedisConnection
import org.springframework.data.redis.connection.RedisConnectionFactory

class RedisHealthCheckerTest {

    private val redisConnection: RedisConnection = mockk(relaxed = true)
    private val redisConnectionFactory: RedisConnectionFactory = mockk()

    private val healthChecker: HealthChecker = RedisHealthChecker(redisConnectionFactory)

    @Test
    fun `isHealthy should return true when Redis ping is successful`() = runTest {
        // Given
        every { redisConnection.ping() } returns "PONG"
        every { redisConnectionFactory.connection } returns redisConnection

        // When
        val result = healthChecker.isHealthy()

        // Then
        assertTrue(result)
        verify {
            redisConnectionFactory.connection
            redisConnection.ping()
        }
        verify(exactly = 1) { redisConnection.close() }
        confirmVerified(redisConnection, redisConnectionFactory)
    }

    @Test
    fun `isHealthy should return false when Redis ping fails`() = runTest {
        // Given
        every { redisConnectionFactory.connection } throws RuntimeException("Connection failed")

        // When
        val result = healthChecker.isHealthy()

        // Then
        assertFalse(result)
        verify { redisConnectionFactory.connection }
        verify(exactly = 0) { redisConnection.close() }
        confirmVerified(redisConnectionFactory)
    }

    @Test
    fun `isHealthy should return false when Redis ping returns null`() = runTest {
        // Given
        every { redisConnection.ping() } returns null
        every { redisConnectionFactory.connection } returns redisConnection

        // When
        val result = healthChecker.isHealthy()

        // Then
        assertFalse(result)
        verify {
            redisConnectionFactory.connection
            redisConnection.ping()
        }
        verify(exactly = 1) { redisConnection.close() }
        confirmVerified(redisConnection, redisConnectionFactory)
    }
}
