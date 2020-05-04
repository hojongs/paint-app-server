package com.hojongs.paint.service

import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.repository.PaintSessionRepository
import com.hojongs.paint.util.reactor.ReactorUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers

@Service
class PaintSessionService(
    private val paintSessionRepository: PaintSessionRepository,
    private val ioScheduler: Scheduler = Schedulers.boundedElastic()
) {
    fun findByIdOrNull(id: String): Mono<PaintSession?> =
        ReactorUtils
            .monoOnScheduler(ioScheduler) { paintSessionRepository.findByIdOrNull(id) }
            .transform { ReactorUtils.withRetry(it) }

    fun createPaintSession(
        name: String,
        password: String
    ): Mono<PaintSession> =
        ReactorUtils
            .monoOnScheduler(ioScheduler) {
                val entity = PaintSession(name, password)

                paintSessionRepository.save(entity)
            }
            .transform { ReactorUtils.withRetry(it) }
}
