package com.hojongs.paint.service

import com.hojongs.paint.exception.AlreadyExistsException
import com.hojongs.paint.repository.model.PaintSession
import com.hojongs.paint.repository.PaintSessionRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*
import kotlin.NoSuchElementException

@Service
class PaintSessionService(
    private val paintSessionRepository: PaintSessionRepository
) {
//    companion object {
//        const val PAGE_SIZE = 100
//    }
//
//    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
//
//    fun findById(id: String): PaintSession {
//        return paintSessionRepository
//            .findById(id)
//            ?: throw NoSuchElementException()
//    }

    fun createSession(
        userId: UUID,
        name: String,
        password: String
    ): Mono<PaintSession> {
        val checkDuplicatedName = paintSessionRepository.findByName(name)
            .flatMap { Mono.error<PaintSession>(AlreadyExistsException(name)) }
        val entity = PaintSession(userId = userId, name = name, password = password)
        val saveSession = paintSessionRepository.save(entity)

        return checkDuplicatedName
            .flatMap { saveSession }
    }

//    fun listSessionPage(pageNumber: Int): List<PaintSession> {
//        return paintSessionRepository
//            .findAll(PageRequest.of(pageNumber, PAGE_SIZE))
//            .content
//    }
//
//    fun joinPaintSession(
//        id: String,
//        userId: UUID
//    ): PaintSession {
//        // todo user service
//        return paintSessionRepository.findByIdOrNull(id)
//            ?: throw NoSuchElementException()
//    }
}
