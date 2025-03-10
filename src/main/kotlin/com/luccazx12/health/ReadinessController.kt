package com.luccazx12.health

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health-check")
class ReadinessController(private val healthCheckers: List<HealthChecker>) {

    @GetMapping
    suspend fun checkReadiness(): ResponseEntity<String> {
        val failedChecker = coroutineScope {
            healthCheckers
                    .map { checker ->
                        async {
                            if (!checker.isHealthy()) "Dependency failed: ${checker.getName()}"
                            else null
                        }
                    }
                    .awaitAll()
                    .firstOrNull { it != null }
        }

        return failedChecker?.let { ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(it) }
                ?: ResponseEntity.ok("All dependencies are healthy!")
    }

    @GetMapping("/{dependencyName}")
    suspend fun checkSpecificDependency(
            @PathVariable dependencyName: String
    ): ResponseEntity<String> {
        val checker = healthCheckers.find { it.getName().equals(dependencyName, ignoreCase = true) }
        return if (checker == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dependency '$dependencyName' not found.")
        } else if (!checker.isHealthy()) {
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Dependency '$dependencyName' is not healthy.")
        } else {
            ResponseEntity.ok("Dependency '$dependencyName' is healthy!")
        }
    }
}
