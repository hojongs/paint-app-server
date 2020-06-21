package com.hojongs.paint.restcontroller

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

//    // todo user 체크
//    @GetMapping("/{id}/join")
//    private fun join(@PathVariable id: String, @RequestParam userId: String): PaintSession =
//        paintSessionService.joinPaintSession(id, UUID.fromString(userId))

    @ExceptionHandler(NoSuchElementException::class)
    private fun exceptionHandler(err: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(err.message, HttpStatus.NO_CONTENT)
}
