package com.hojongs.paint.service

import com.hojongs.paint.exception.AlreadyExistsException
import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PaintUserService(
    private val paintUserRepository: PaintUserRepository
) {

    fun createUser(email: String, password: String): Mono<PaintUser> {
        val checkExists = paintUserRepository
            .findByEmail(email)
            .flatMap { Mono.error<PaintUser>(AlreadyExistsException(email)) }
        val paintUser = PaintUser(email, password)
        val saveUser = paintUserRepository
            .insert(paintUser)
            .onErrorMap({ it is DuplicateKeyException }) { AlreadyExistsException(paintUser.id, it) }

        return checkExists
            .switchIfEmpty(saveUser)
    }

    fun signIn(email: String, password: String): Mono<PaintUser> =
        paintUserRepository
            .findByEmailAndPassword(email, password)
            .switchIfEmpty(Mono.error(NoSuchElementException("PaintUser")))
}
