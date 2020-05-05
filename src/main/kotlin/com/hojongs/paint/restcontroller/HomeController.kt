package com.hojongs.paint.restcontroller

import com.hojongs.paint.util.logger.PaintLogger
import com.hojongs.paint.util.reactor.ReactorUtils
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class HomeController {
    companion object : PaintLogger()

    @GetMapping
    private fun home(authentication: Authentication): Mono<String> =
        ReactorUtils.mono { "Hello, ${authentication.name}" }
}
