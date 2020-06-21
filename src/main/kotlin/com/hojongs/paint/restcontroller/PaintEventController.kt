package com.hojongs.paint.restcontroller

import com.hojongs.paint.exception.NotJoinedSessionException
import com.hojongs.paint.model.PaintEvent
import com.hojongs.paint.service.AuthenticationService
import com.hojongs.paint.service.PaintEventService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/events/")
class PaintEventController(
    private val paintEventService: PaintEventService,
    private val authenticationService: AuthenticationService
) {
    @GetMapping("/notify")
    fun notifyEvent(
        @RequestParam userId: UUID,
        @RequestParam eventTypeString: String
    ): Mono<PaintEvent> {
        return authenticationService
            .authenticate(userId)
            .flatMap {
                Mono.zip(
                    Mono.just(it),
                    Mono.fromCallable { PaintEvent.Type.valueOf(eventTypeString) }
                        .onErrorReturn(PaintEvent.Type.NONE)
                )
            }
            .flatMap {
                val user = it.t1
                val eventType = it.t2
                val joinedSessionId = user.joinedSessionId
                if (joinedSessionId == null)
                    Mono.error(NotJoinedSessionException(user.id))
                else
                    paintEventService.notifyEvent(user.id, joinedSessionId, eventType)
            }
    }
}