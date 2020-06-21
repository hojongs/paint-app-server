package com.hojongs.paint.restcontroller

import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.model.PaintUser
import com.hojongs.paint.service.AuthenticationService
import com.hojongs.paint.service.PaintUserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/users")
class PaintUserController(
    private val paintUserService: PaintUserService,
    private val authenticationService: AuthenticationService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/create")
    fun createUser(
        @RequestParam email: String,
        @RequestParam password: String
    ): Mono<UUID> {
        if (email.isEmpty())
            throw IllegalArgumentException("email=$email")
        if (password.isEmpty())
            throw IllegalArgumentException("pw=$password")

        return paintUserService.createUser(email, password)
            .map { it.id }
    }

    @GetMapping("/sessions/join")
    private fun join(
        @RequestParam userId: UUID,
        @PathVariable sessionName: String,
        @PathVariable sessionPassword: String
    ): Mono<PaintUser> {
        return authenticationService
            .authenticate(userId)
            .flatMap { paintUserService.joinSession(userId, sessionName, sessionPassword) }
    }
}
