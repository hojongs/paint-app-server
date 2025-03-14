package com.hojongs.paint.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.UUID

@Document("paint_users")
open class PaintUser(
    @Id
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val password: String,
    joinedSessionId: UUID? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(UTC)
) : BaseEntity(), Cloneable {
    var joinedSessionId = joinedSessionId
        private set

    fun joinSession(sessionId: UUID): PaintUser {
        return this.clone().apply {
            joinedSessionId = sessionId
        }
    }

    fun exitSession(): PaintUser {
        return this.clone().apply {
            joinedSessionId = null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is PaintUser && other.id == id)
            return true

        return super.equals(other)
    }

    override fun clone(): PaintUser {
        return super.clone() as PaintUser
    }
}
