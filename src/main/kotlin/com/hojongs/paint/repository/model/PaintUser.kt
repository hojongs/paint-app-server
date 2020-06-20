package com.hojongs.paint.repository.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.UUID

@Document("paint_users")
class PaintUser(
    @Id
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val password: String,
    val createdAt: LocalDateTime = LocalDateTime.now(UTC)
) {
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
