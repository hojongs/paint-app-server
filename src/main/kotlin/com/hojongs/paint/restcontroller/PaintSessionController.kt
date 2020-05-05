package com.hojongs.paint.restcontroller

import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.impl.session.PaintSessionService
import com.hojongs.paint.util.logger.LoggerUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/sessions")
class PaintSessionController(
    private val paintSessionService: PaintSessionService
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    // todo user 체크 & session에 대한 권한 체크
    @GetMapping("/{id}")
    private fun findById(@PathVariable id: String): Mono<PaintSession> =
        paintSessionService
            .findByIdOrNull(id)
            .transform { LoggerUtils.logError(logger, it) }

    // todo user 체크
    @GetMapping("/create")
    private fun createOne(@RequestParam name: String, @RequestParam password: String): Mono<PaintSession> =
        paintSessionService
            .createPaintSession(name, password)
            .transform { LoggerUtils.logError(logger, it) }

    // todo user 체크
    @GetMapping
    private fun listPage(@RequestParam(defaultValue = "0") pageNumber: Int): Mono<List<PaintSession>> =
        paintSessionService
            .listPage(pageNumber)
            .collectList()
            .transform { LoggerUtils.logError(logger, it) }

    // todo user 체크
    @GetMapping("/{id}/join")
    private fun join(@PathVariable id: String, @RequestParam userId: String): Mono<PaintSession> =
        paintSessionService
            .joinPaintSession(id, UUID.fromString(userId))
            .transform { LoggerUtils.logError(logger, it) }

    @ExceptionHandler(NoSuchElementException::class)
    private fun exceptionHandler(err: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(err.message, HttpStatus.NO_CONTENT)
}
