package com.hojongs.paint.restcontroller

import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.service.PaintSessionService
import com.hojongs.paint.util.logger.LoggerUtils
import com.hojongs.paint.util.logger.PaintLogger
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/sessions")
class PaintSessionController(
    private val paintSessionService: PaintSessionService
) {
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

    companion object : PaintLogger()
}
