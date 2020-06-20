package com.hojongs.paint.service

import com.hojongs.paint.exception.AlreadyExistsException
import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.will
import org.springframework.dao.DuplicateKeyException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import java.util.UUID

internal class PaintUserServiceTest {
    private val paintUserRepository = mock<PaintUserRepository>()
    private val paintUserService = spy(PaintUserService(paintUserRepository))

    @Test
    fun `given exists email when createUser() then AlreadyExistsException(email) error`() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()
        given(paintUserRepository.findByEmail(email))
            .will { PaintUser(email = email, password = password).toMono() }
        given(paintUserRepository.insert(any<PaintUser>()))
            .will { Mono.empty<PaintUser>() }

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
        given(paintUserRepository.findByEmail(email))
            .will { Mono.empty<PaintUser>() }
        given(paintUserRepository.insert(any<PaintUser>()))
            .will { Mono.error<PaintUser>(DuplicateKeyException("")) }

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
        given(paintUserRepository.findByEmail(email))
            .will { Mono.empty<PaintUser>() }
        given(paintUserRepository.insert(any<PaintUser>()))
            .will { PaintUser(email = email, password = password).toMono() }

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
        given(paintUserRepository.findByEmailAndPassword(email, password))
            .will { PaintUser(email = email, password = password).toMono() }

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
        given(paintUserRepository.findByEmailAndPassword(email, password))
            .will { Mono.empty<PaintUser>() }

        // when
        StepVerifier.create(paintUserService.signIn(email, password))
            // then
            .verifyErrorMatches { it is NoSuchElementException }
    }

    @Test
    fun `given not exists user id when getUser() then empty`() {
        // given
        val id = UUID.randomUUID()
        given(paintUserRepository.findById(id))
            .will { Mono.empty<PaintUser>() }

        // when
        StepVerifier.create(paintUserService.getUser(id))
            // then
            .verifyComplete()
    }

    @Test
    fun `given exists user id when getUser() then success`() {
        // given
        val id = UUID.randomUUID()
        val user = PaintUser(email = "ema", password = "pas")
        given(paintUserRepository.findById(id))
            .will { user.toMono() }

        // when
        StepVerifier.create(paintUserService.getUser(id))
            // then
            .assertNext { it shouldBe user }
            .verifyComplete()
    }

    @Test
    fun `given not exists user id when deleteUser() then NoSuchElementException error`() {
        // given
        val id = UUID.randomUUID()
        given(paintUserRepository.findById(id))
            .will { Mono.empty<PaintUser>() }

        // when
        StepVerifier.create(paintUserService.deleteUser(id))
            // then
            .verifyErrorMatches { it is NoSuchElementException }
    }

    @Test
    fun `given exists user id when deleteUser() then success`() {
        // given
        val id = UUID.randomUUID()
        val user = PaintUser(id = id, email = "ema", password = "pas")
        will { user.toMono() }.given(paintUserService).getUser(id)
        given(paintUserRepository.deleteById(id))
            .will { Mono.empty<Void>() }

        // when
        StepVerifier.create(paintUserService.deleteUser(id))
            // then
            .verifyComplete()
    }
}
