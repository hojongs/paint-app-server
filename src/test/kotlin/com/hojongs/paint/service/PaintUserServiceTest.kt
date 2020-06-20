package com.hojongs.paint.service

import com.hojongs.paint.exception.AlreadyExistsException
import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.dao.DuplicateKeyException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import java.util.UUID

internal class PaintUserServiceTest {
    private val paintUserRepository = mock<PaintUserRepository>()
    private val paintUserService = PaintUserService(paintUserRepository)

    @Test
    fun `given exists email when createUser() then AlreadyExistsException(email) error`() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()
        whenever(paintUserRepository.findByEmail(email))
            .then { PaintUser(email, password).toMono() }
        whenever(paintUserRepository.insert(any<PaintUser>()))
            .then { Mono.empty<PaintUser>() }

        // when
        StepVerifier.create(paintUserService.createUser(email, password))
            // then
            .verifyErrorMatches { it is AlreadyExistsException && it.key == email }
    }

    @Test
    fun `given exists id when createUser() then AlreadyExistsException error`() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()
        whenever(paintUserRepository.findByEmail(email))
            .then { Mono.empty<PaintUser>() }
        whenever(paintUserRepository.insert(any<PaintUser>()))
            .then { Mono.error<PaintUser>(DuplicateKeyException("")) }

        // when
        StepVerifier.create(paintUserService.createUser(email, password))
            // then
            .verifyErrorMatches { it is AlreadyExistsException }
    }

    @Test
    fun `given new user when createUser() then success`() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()
        whenever(paintUserRepository.findByEmail(email))
            .then { Mono.empty<PaintUser>() }
        whenever(paintUserRepository.insert(any<PaintUser>()))
            .then { PaintUser(email, password).toMono() }

        // when
        StepVerifier.create(paintUserService.createUser(email, password))
            // then
            .assertNext { createdUser ->
                createdUser.email shouldBe email
                createdUser.password shouldBe password
            }
            .verifyComplete()
    }

    @Test
    fun `given exists user when signIn() then success`() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()
        whenever(paintUserRepository.findByEmailAndPassword(email, password))
            .then { PaintUser(email, password).toMono() }

        // when
        StepVerifier.create(paintUserService.signIn(email, password))
            // then
            .assertNext { createdUser ->
                createdUser.email shouldBe email
                createdUser.password shouldBe password
            }
            .verifyComplete()
    }

    @Test
    fun `given not exists user when signIn() then NoSuchElementException error`() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()
        whenever(paintUserRepository.findByEmailAndPassword(email, password))
            .then { Mono.empty<PaintUser>() }

        // when
        StepVerifier.create(paintUserService.signIn(email, password))
            // then
            .verifyErrorMatches { it is NoSuchElementException }
    }

    @Test
    fun `given not exists user id when getUser() then empty`() {
        // given
        val id = UUID.randomUUID()
        whenever(paintUserRepository.findById(id))
            .then { Mono.empty<PaintUser>() }

        // when
        StepVerifier.create(paintUserService.getUser(id))
            // then
            .verifyComplete()
    }

    @Test
    fun `given exists user id when getUser() then success`() {
        // given
        val id = UUID.randomUUID()
        val user = PaintUser("ema", "pas")
        whenever(paintUserRepository.findById(id))
            .then { user.toMono() }

        // when
        StepVerifier.create(paintUserService.getUser(id))
            // then
            .assertNext { it shouldBe user }
            .verifyComplete()
    }
}
