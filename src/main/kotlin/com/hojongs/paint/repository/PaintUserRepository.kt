package com.hojongs.paint.repository

import com.hojongs.paint.repository.model.PaintUser
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.UUID

@Configuration
interface PaintUserRepository : ReactiveMongoRepository<PaintUser, UUID>
