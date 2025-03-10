package com.luccazx12.health

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ReadinessControllerTest {

    private val healthCheckers: List<HealthChecker> = listOf(mockk<HealthChecker>())
    private val readinessController = ReadinessController(healthCheckers)

    @Test
    fun `checkReadiness should return OK when all dependencies are healthy`() = runTest {
        // Given
        coEvery { healthCheckers[0].isHealthy() } returns true
        coEvery { healthCheckers[0].getName() } returns "testDependency"

        // When
        val response = readinessController.checkReadiness()

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("All dependencies are healthy!", response.body)
    }

    @Test
    fun `checkReadiness should return SERVICE_UNAVAILABLE when a dependency is not healthy`() =
            runTest {
                // Given
                coEvery { healthCheckers[0].isHealthy() } returns false
                coEvery { healthCheckers[0].getName() } returns "testDependency"

                // When
                val response = readinessController.checkReadiness()

                // Then
                assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.statusCode)
                assertEquals("Dependency failed: testDependency", response.body)
            }

    @Test
    fun `checkSpecificDependency should return OK when the specified dependency is healthy`() =
            runTest {
                // Given
                coEvery { healthCheckers[0].getName() } returns "testDependency"
                coEvery { healthCheckers[0].isHealthy() } returns true

                // When
                val response = readinessController.checkSpecificDependency("testDependency")

                // Then
                assertEquals(HttpStatus.OK, response.statusCode)
                assertEquals("Dependency 'testDependency' is healthy!", response.body)
            }

    @Test
    fun `checkSpecificDependency should return NOT_FOUND when the dependency does not exist`() =
            runTest {
                // Given
                coEvery { healthCheckers[0].getName() } returns "testDependency"

                // When
                val response = readinessController.checkSpecificDependency("nonExistentDependency")

                // Then
                assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
                assertEquals("Dependency 'nonExistentDependency' not found.", response.body)
            }

    @Test
    fun `checkSpecificDependency should return SERVICE_UNAVAILABLE when the specified dependency is not healthy`() =
            runTest {
                // Given
                coEvery { healthCheckers[0].getName() } returns "testDependency"
                coEvery { healthCheckers[0].isHealthy() } returns false

                // When
                val response = readinessController.checkSpecificDependency("testDependency")

                // Then
                assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.statusCode)
                assertEquals("Dependency 'testDependency' is not healthy.", response.body)
            }
}
