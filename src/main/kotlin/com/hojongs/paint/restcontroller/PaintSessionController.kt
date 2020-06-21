package com.hojongs.paint.restcontroller

import com.hojongs.paint.exception.NotExistsException
import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.service.AuthenticationService
import com.hojongs.paint.service.PaintSessionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.UUID
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/sessions")
class PaintSessionController(
    private val paintSessionService: PaintSessionService,
    private val authenticationService: AuthenticationService
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/create")
    private fun createSession(
        @RequestParam userId: UUID,
        @RequestParam name: String,
        @RequestParam password: String
    ): Mono<PaintSession> {
        return authenticationService
            .authenticate(userId)
            .flatMap { paintSessionService.createSession(userId, name, password) }
    }

    @GetMapping
    private fun listSession(
        @RequestParam userId: UUID
    ): Mono<List<PaintSession>> {
        return authenticationService
            .authenticate(userId)
            .flatMap { paintSessionService.listSession().collectList() }
    }

    @ExceptionHandler(NotExistsException::class)
    private fun exceptionHandler(err: NotExistsException): ResponseEntity<String> =
        ResponseEntity(err.message, HttpStatus.NO_CONTENT)
}
