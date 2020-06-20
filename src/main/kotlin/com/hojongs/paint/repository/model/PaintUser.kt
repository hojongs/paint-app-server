package com.hojongs.paint.repository.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("paint_users")
class PaintUser private constructor(
    @Id
    val id: UUID,
    val email: String,
    val password: String
) {
    constructor(email: String, password: String) : this(UUID.randomUUID(), email, password)

    override fun toString(): String = "PaintUser(id=$id,email=$email,password=$password)"
}
