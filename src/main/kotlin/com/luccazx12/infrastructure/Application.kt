package com.luccazx12.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
// Realiza a varredura de pacotes para encontrar os componentes que serão gerenciados pelo Spring
@ComponentScan(
        basePackages =
                [
                        "com.luccazx12.infrastructure",
                        "com.luccazx12.health",
                        "com.luccazx12.springkotlinbff"]
)
class Application

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
