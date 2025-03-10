package com.luccazx12.health

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class LivenessController {

  @GetMapping
  fun checkLiveness(): ResponseEntity<Void> {
    return ResponseEntity.ok().build()
  }
}
