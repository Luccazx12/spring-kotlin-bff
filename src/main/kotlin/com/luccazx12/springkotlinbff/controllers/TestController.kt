package com.luccazx12.springkotlinbff.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

class MyCustomException(message: String) : Exception(message)

@RestController
@RequestMapping("/v1/test")
class TestController {

    @GetMapping
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok("Test route is working!")
    }

    @GetMapping("/throw-exception")
    fun throwException(): String {
        throw MyCustomException("Test exception")
    }
}
