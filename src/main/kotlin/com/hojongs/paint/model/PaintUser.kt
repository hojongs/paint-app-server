package com.hojongs.paint.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "paint_users")
data class PaintUser(
    @Id
    val id: UUID = UUID.randomUUID(),
    private val nickname: String,
    private val password: String
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> =
        AuthorityUtils.createAuthorityList(
            "ROLE_USER"
        )

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = id.toString()

    override fun getPassword(): String = password

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false
}
