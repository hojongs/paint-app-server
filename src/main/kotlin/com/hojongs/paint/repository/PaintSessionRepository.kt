package com.hojongs.paint.repository

import com.hojongs.paint.repository.model.PaintSession
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.UUID

@Configuration
interface PaintSessionRepository : ReactiveMongoRepository<PaintSession, UUID> {
    fun findByName(name: String): Mono<PaintSession>
}
