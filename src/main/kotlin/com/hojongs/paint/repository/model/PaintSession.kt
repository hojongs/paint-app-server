package com.hojongs.paint.repository.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("paint_sessions")
class PaintSession private constructor(
    @Id
    val id: Long,
    val name: String,
    val password: String
) {
    constructor(name: String, password: String) : this(0, name, password)
}
