package com.hojongs.paint.repository.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "paint_users")
data class PaintUser(
    @Id
    private val email: String,
    private val displayName: String,
    private val password: String,
    private val authorities: List<GrantedAuthority> =
        AuthorityUtils.createAuthorityList(
            "ROLE_USER"
        ),
    private val isEnabled: Boolean = true
) : UserDetails {

    override fun getUsername(): String = email

    override fun getPassword(): String = password

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun isEnabled(): Boolean = isEnabled

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}
