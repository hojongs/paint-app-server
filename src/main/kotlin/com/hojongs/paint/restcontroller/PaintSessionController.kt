package com.hojongs.paint.restcontroller

import com.hojongs.paint.model.PaintSession
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/sessions")
class PaintSessionController {
    @GetMapping("/{id}")
    private fun getPaintSessionById(@PathVariable id: String): Mono<PaintSession> =
        Mono.just(PaintSession(id))
}
