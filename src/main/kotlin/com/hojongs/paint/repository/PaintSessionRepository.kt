package com.hojongs.paint.repository

import com.hojongs.paint.repository.model.PaintSession
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

@Configuration
interface PaintSessionRepository : ReactiveMongoRepository<PaintSession, Long>
