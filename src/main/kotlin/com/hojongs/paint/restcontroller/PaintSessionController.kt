package com.hojongs.paint.restcontroller

import com.hojongs.paint.repository.model.PaintSession
import com.hojongs.paint.service.PaintSessionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.NoSuchElementException

@RequestMapping("/sessions")
class PaintSessionController(
    private val paintSessionService: PaintSessionService
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    // todo user 체크 & session에 대한 권한 체크
    @GetMapping("/{id}")
    private fun findById(@PathVariable id: String): PaintSession {
        return paintSessionService.findById(id)
    }

    // todo user 체크
    @GetMapping("/create")
    private fun createOne(@RequestParam name: String, @RequestParam password: String): PaintSession {
        return paintSessionService.createPaintSession(name, password)
    }

    // todo user 체크
    @GetMapping
    private fun listPage(@RequestParam(defaultValue = "0") pageNumber: Int): List<PaintSession> =
        paintSessionService.listSessionPage(pageNumber)

    // todo user 체크
    @GetMapping("/{id}/join")
    private fun join(@PathVariable id: String, @RequestParam userId: String): PaintSession =
        paintSessionService.joinPaintSession(id, UUID.fromString(userId))

    @ExceptionHandler(NoSuchElementException::class)
    private fun exceptionHandler(err: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(err.message, HttpStatus.NO_CONTENT)
}
