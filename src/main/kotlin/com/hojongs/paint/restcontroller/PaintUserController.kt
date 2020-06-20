package com.hojongs.paint.restcontroller

import com.hojongs.paint.repository.model.PaintUser
import com.hojongs.paint.service.PaintUserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class PaintUserController(
    private val paintUserService: PaintUserService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

//    @GetMapping("/create")
//    fun createUser(
//        @RequestParam email: String,
//        @RequestParam password: String,
//        @RequestParam("displayName") displayNameOrNull: String?
//    ): Mono<PaintUser> {
//        if (email.isEmpty())
//            throw IllegalArgumentException("email=$email")
//        if (password.isEmpty())
//            throw IllegalArgumentException("password=$password")
//
//        val displayName = displayNameOrNull
//            ?: email.also { logger.info("displayName was null, replaced with email($email)") }
//
//        return paintUserService.signUp(email, password, displayName)
//    }
}
