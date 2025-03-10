package com.luccazx12.infrastructure.middlewares

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionMiddleware : ResponseEntityExceptionHandler() {

    private val logger = LoggerFactory.getLogger(GlobalExceptionMiddleware::class.java)

    @ExceptionHandler(Exception::class)
    fun handleServerError(exception: Exception): ResponseEntity<Any> {
        logError(exception)
        return returnInternalServerError()
    }

    @Suppress("TooGenericExceptionCaught")
    fun logError(exception: Exception) {
        try {
            logger.error("UnhandledError: ${exception.message}", exception)
        } catch (e: Exception) {
            logger.error("Failed to log error: ${e.message}", e)
        }
    }

    fun returnInternalServerError(): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Internal server error"))
    }
}
