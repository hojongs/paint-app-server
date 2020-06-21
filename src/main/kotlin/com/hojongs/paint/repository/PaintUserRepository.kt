package com.hojongs.paint.repository

import com.hojongs.paint.model.PaintUser
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface PaintUserRepository : ReactiveMongoRepository<PaintUser, UUID> {
    fun findByEmail(email: String): Mono<PaintUser>
    fun findByEmailAndPassword(email: String, password: String): Mono<PaintUser>
    fun findByJoinedSessionId(joinedSessionId: UUID): Flux<PaintUser>
}
