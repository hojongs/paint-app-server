package com.hojongs.paint.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("paint_events")
open class PaintEvent(
    @Id
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val sessionId: UUID,
    val eventType: String
) {
    enum class Type {
        NONE
    }
}
