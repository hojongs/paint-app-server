package com.hojongs.paint.service

import com.hojongs.paint.model.PaintEvent
import com.hojongs.paint.repository.PaintEventRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class PaintEventService(
    private val paintEventRepository: PaintEventRepository
) {
    fun notifyEvent(
        userId: UUID,
        sessionId: UUID,
        eventType: PaintEvent.Type
    ): Mono<PaintEvent> {
        val logEvent = Mono.empty<PaintEvent>()
        val sendEvent = Mono.empty<PaintEvent>()

        return logEvent
            .flatMap { sendEvent }
    }
}