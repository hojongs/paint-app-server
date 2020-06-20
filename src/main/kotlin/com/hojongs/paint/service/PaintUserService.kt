package com.hojongs.paint.service

import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PaintUserService(
    private val paintUserRepository: PaintUserRepository
) {

    fun createUser(email: String, password: String): Mono<PaintUser> {
        val paintUser = PaintUser(email, password)

        return paintUserRepository
            .insert(paintUser)
            .onErrorMap { Exception("user already exists") }
    }

//    fun signIn(userId: String, password: String): PaintUser =
//        paintUserRepository
//            .findByIdOrNull(userId)
//            ?.takeIf { foundUser: PaintUser -> foundUser.password != password }
//            ?: throw NoSuchElementException("PaintUser")
}
