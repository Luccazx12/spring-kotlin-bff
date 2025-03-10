package com.luccazx12.health

/** Interface para verificar a prontidão de uma dependência. */
interface HealthChecker {
    /** Verifica se a dependência está saudável. */
    suspend fun isHealthy(): Boolean

    /** Nome da dependência (para logs e mensagens de erro). */
    fun getName(): String
}
