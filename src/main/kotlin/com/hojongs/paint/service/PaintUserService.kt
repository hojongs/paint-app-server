package com.hojongs.paint.service

import com.hojongs.paint.exception.AlreadyExistsException
import com.hojongs.paint.repository.PaintSessionRepository
import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintSession
import com.hojongs.paint.repository.model.PaintUser
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class PaintUserService(
    private val paintUserRepository: PaintUserRepository,
    private val paintSessionRepository: PaintSessionRepository
) {

    fun createUser(email: String, password: String): Mono<PaintUser> {
        val checkExists = paintUserRepository
            .findByEmail(email)
            .flatMap { Mono.error<PaintUser>(AlreadyExistsException(email)) }
        val paintUser = PaintUser(email = email, password = password)
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

    fun getUser(id: UUID): Mono<PaintUser> {
        return paintUserRepository.findById(id)
    }

    // Mono<Void> == Mono.empty()
    fun deleteUser(id: UUID): Mono<Void> {
        return getUser(id)
            .switchIfEmpty(Mono.error(NoSuchElementException(id.toString())))
            .flatMap { paintUserRepository.deleteById(id) }
    }

    fun joinSession(
        userId: UUID,
        name: String,
        password: String
    ): Mono<PaintUser> {
        val findUser = paintUserRepository.findById(userId)
            .switchIfEmpty(Mono.error(java.util.NoSuchElementException("userId=$userId")))
        val findSession = paintSessionRepository.findByNameAndPassword(name, password)
            .switchIfEmpty(Mono.error(java.util.NoSuchElementException("name=$name")))
        return Mono
            .zip(
                findUser,
                findSession
            )
            .flatMap {
                val foundUser = it.t1
                val foundSession = it.t2
                val joinedUser = foundUser.joinSession(foundSession.id)
                paintUserRepository.save(joinedUser)
            }
    }
}
