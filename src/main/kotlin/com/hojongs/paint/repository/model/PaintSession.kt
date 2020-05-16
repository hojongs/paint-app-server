package com.hojongs.paint.repository.model

import javax.persistence.Entity
import javax.persistence.Id
import kotlin.random.Random

@Entity(name = "paint_sessions")
data class PaintSession(
    val name: String,

    val password: String,

    @Id
    val id: String = "$name-${Random.nextLong(0, 99999+1)}"
)
