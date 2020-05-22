package com.hojongs.paint.repository.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "paint_users")
data class PaintUser(
    @Id
    private val email: String,
    private val displayName: String,
    private val password: String,
    private val isEnabled: Boolean = true
) {

    fun getUsername(): String = email

    fun getPassword(): String = password

    fun isEnabled(): Boolean = isEnabled

    fun getAuthorities(): List<String> = listOf("ROLE_USER")

    fun isCredentialsNonExpired(): Boolean = true

    fun isAccountNonExpired(): Boolean = true

    fun isAccountNonLocked(): Boolean = true
}
