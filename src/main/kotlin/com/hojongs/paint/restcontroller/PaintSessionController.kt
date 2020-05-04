package com.hojongs.paint.restcontroller

import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.service.PaintSessionService
import com.hojongs.paint.util.logger.LoggerUtils
import com.hojongs.paint.util.logger.PaintLogger
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
    companion object : PaintLogger()

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
