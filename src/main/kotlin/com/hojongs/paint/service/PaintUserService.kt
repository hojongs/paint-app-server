package com.hojongs.paint.service

import com.hojongs.paint.repository.PaintUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class PaintUserService(
    private val paintUserRepository: PaintUserRepository
) : UserDetailsService {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun loadUserByUsername(username: String): UserDetails =
        paintUserRepository
            .findByIdOrNull(username)
            ?: throw UsernameNotFoundException(username)
}
