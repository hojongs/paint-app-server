package com.hojongs.paint.repository.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.UUID

@Document("paint_users")
class PaintUser private constructor(
    @Id
    val id: UUID,
    val email: String,
    val password: String,
    val createdAt: LocalDateTime
) {
    constructor(
        email: String,
        password: String,
        createdAt: LocalDateTime = LocalDateTime.now(UTC)
    ) : this(UUID.randomUUID(), email, password, createdAt)

    override fun toString(): String {
        val className = javaClass.simpleName
        val fields = javaClass.declaredFields.map {
            val fieldName = it.name
            val fieldValue = it.get(this)
            "$fieldName=$fieldValue"
        }
        return "$className($fields)"
    }
}
