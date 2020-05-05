package com.hojongs.paint.restcontroller

import com.hojongs.paint.util.reactor.ReactorUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class HomeController {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    private fun home(authentication: Authentication): Mono<String> =
        ReactorUtils.mono { "Hello, ${authentication.name}" }
}
