package com.hojongs.paint.service

import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.repository.PaintSessionRepository
import com.hojongs.paint.util.logger.PaintLogger
import com.hojongs.paint.util.reactor.ReactorUtils
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.util.*
import kotlin.NoSuchElementException

@Service
class PaintSessionService(
    private val paintSessionRepository: PaintSessionRepository,
    private val ioScheduler: Scheduler = Schedulers.boundedElastic()
) {
    companion object : PaintLogger() {
        const val PAGE_SIZE = 100
    }

    fun findByIdOrNull(id: String): Mono<PaintSession> =
        ReactorUtils
            .monoOnScheduler(ioScheduler) { paintSessionRepository.findByIdOrNull(id) }
            .transform { ReactorUtils.withRetry(it) }
            .switchIfEmpty(Mono.error(NoSuchElementException()))

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

    fun listPage(pageNumber: Int): Flux<PaintSession> =
        Flux
            .fromStream {
                paintSessionRepository
                    .findAll(PageRequest.of(pageNumber, PAGE_SIZE))
                    .get()
            }
            .subscribeOn(ioScheduler)
            .transform { ReactorUtils.withRetry(it) }
            .switchIfEmpty(Mono.error(NoSuchElementException()))

    fun joinPaintSession(
        id: String,
        userId: UUID
    ): Mono<PaintSession> =
        // todo user service
        ReactorUtils
            .monoOnScheduler(ioScheduler) { paintSessionRepository.findByIdOrNull(id) }
}
