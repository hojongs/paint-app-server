package com.hojongs.paint.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import kotlin.random.Random

@Entity(name = "paint_users")
data class PaintUser(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val password: String
)
