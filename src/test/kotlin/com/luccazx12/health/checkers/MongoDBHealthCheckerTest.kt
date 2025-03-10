package com.luccazx12.health.checkers

import com.mongodb.client.MongoClient
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MongoDBHealthCheckerTest {

    private val mongoClient: MongoClient = mockk()
    private val healthChecker = MongoDBHealthChecker(mongoClient)

    @Test
    fun `isHealthy should return true when MongoDB is accessible`() = runTest {
        // Given
        every { mongoClient.listDatabaseNames().iterator().hasNext() } returns true

        // When
        val result = healthChecker.isHealthy()

        // Then
        assertTrue(result)
        verify { mongoClient.listDatabaseNames().iterator().hasNext() }
    }

    @Test
    fun `isHealthy should return false when MongoDB throws an exception`() = runTest {
        // Given
        every { mongoClient.listDatabaseNames() } throws RuntimeException("Connection failed")

        // When
        val result = healthChecker.isHealthy()

        // Then
        assertFalse(result)
        verify { mongoClient.listDatabaseNames() }
    }
}
