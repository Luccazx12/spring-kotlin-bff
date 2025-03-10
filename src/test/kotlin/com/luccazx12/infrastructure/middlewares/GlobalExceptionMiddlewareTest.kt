package com.luccazx12.infrastructure.middlewares

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class GlobalExceptionMiddlewareTest {

    private val middleware = GlobalExceptionMiddleware()

    @Test
    fun `handleServerError should return INTERNAL_SERVER_ERROR and error message`() {
        // Given
        val exception = Exception("Simulated server error")

        // When
        val response = middleware.handleServerError(exception)

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(mapOf("error" to "Internal server error"), response.body)
    }
}
