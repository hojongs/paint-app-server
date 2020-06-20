package com.hojongs.paint.repository.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

@Document("paint_sessions")
open class PaintSession(
    @Id
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val name: String,
    val password: String,
    val createdAt: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
) : BaseEntity()
