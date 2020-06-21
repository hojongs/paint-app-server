package com.hojongs.paint.repository

import com.hojongs.paint.model.PaintEvent
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.UUID

interface PaintEventRepository : ReactiveMongoRepository<PaintEvent, UUID>
