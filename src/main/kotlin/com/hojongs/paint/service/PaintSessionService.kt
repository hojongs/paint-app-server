package com.hojongs.paint.service

import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.repository.PaintSessionRepository
import org.springframework.stereotype.Service

@Service
class PaintSessionService(
    private val paintSessionRepository: PaintSessionRepository
) {
    fun getOne(id: String): PaintSession = paintSessionRepository.getOne(id)

    fun createPaintSession(
        name: String,
        password: String
    ): PaintSession {
        val entity = PaintSession(name, password)

        return paintSessionRepository.save(entity)
    }
}
