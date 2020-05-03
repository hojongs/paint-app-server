package com.hojongs.paint.restcontroller

import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.service.PaintSessionService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/sessions")
class PaintSessionController(
    private val paintSessionService: PaintSessionService
) {
    // todo user 체크 & session에 대한 권한 체크
    @GetMapping("/{id}")
    private fun getPaintSessionById(@PathVariable id: String): Mono<PaintSession> =
        Mono
            .fromCallable {
                paintSessionService.getOne(id)
            }

    // todo user 체크
    @GetMapping("/create")
    @PostMapping("/")
    private fun createOne(@RequestParam name: String, @RequestParam password: String): Mono<PaintSession> =
        Mono
            .fromCallable {
                paintSessionService.createPaintSession(name, password)
            }
}
