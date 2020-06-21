package com.hojongs.paint.service

import com.hojongs.paint.model.PaintEvent
import com.hojongs.paint.repository.PaintEventRepository
import com.hojongs.paint.repository.PaintUserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class PaintEventService(
    private val paintEventRepository: PaintEventRepository,
    private val paintUserRepository: PaintUserRepository
) {
    fun notifyEvent(
        userId: UUID,
        sessionId: UUID,
        eventType: PaintEvent.Type
    ): Mono<PaintEvent> {
        val paintEvent = PaintEvent(
            userId = userId,
            sessionId = sessionId,
            eventType = eventType.name
        )
        val logEvent = paintEventRepository.save(paintEvent)
        // todo send event to users
        val sendEventToSessionUsers = paintUserRepository
            .findByJoinedSessionId(sessionId)

        return logEvent
            .doOnNext { sendEventToSessionUsers.subscribe() }
    }
}