package com.hojongs.paint.service

import com.hojongs.paint.repository.model.PaintSession
import com.hojongs.paint.repository.PaintSessionRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import java.util.*
import kotlin.NoSuchElementException

class PaintSessionService(
    private val paintSessionRepository: PaintSessionRepository
) {
    companion object {
        const val PAGE_SIZE = 100
    }

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun findById(id: String): PaintSession {
        return paintSessionRepository
            .findByIdOrNull(id)
            ?: throw NoSuchElementException()
    }

    fun createPaintSession(
        name: String,
        password: String
    ): PaintSession {
        val entity = PaintSession(name, password)

        return paintSessionRepository.save(entity)
    }

    fun listSessionPage(pageNumber: Int): List<PaintSession> {
        return paintSessionRepository
            .findAll(PageRequest.of(pageNumber, PAGE_SIZE))
            .content
    }

    fun joinPaintSession(
        id: String,
        userId: UUID
    ): PaintSession {
        // todo user service
        return paintSessionRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException()
    }
}
