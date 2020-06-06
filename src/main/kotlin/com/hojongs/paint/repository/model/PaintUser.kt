package com.hojongs.paint.repository.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "paint_users")
class PaintUser(
    @Id
    val email: String = "",
    val password: String = "",
    val displayName: String = email,
    val isEnabled: Boolean = true
) {

    override fun toString(): String = "${this::class}(email=$email,password=$password,displayName=$displayName,isEnabled=$isEnabled)"
}
