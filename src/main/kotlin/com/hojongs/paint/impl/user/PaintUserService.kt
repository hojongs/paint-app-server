package com.hojongs.paint.impl.user

import com.hojongs.paint.repository.PaintUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.util.*

@Service
class PaintUserService(
    private val paintUserRepository: PaintUserRepository,
    private val ioScheduler: Scheduler = Schedulers.boundedElastic()
) : UserDetailsService {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun loadUserByUsername(username: String): UserDetails =
        paintUserRepository
            .findByIdOrNull(UUID.fromString(username))
            ?: throw UsernameNotFoundException(username)
}
