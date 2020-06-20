package com.hojongs.paint.service

import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import java.util.UUID

internal class PaintUserServiceTest {
    private val paintUserRepository = mock<PaintUserRepository> {
        whenever(mock.insert(any<PaintUser>())).then { it.getArgument<PaintUser>(0).toMono() }
        whenever(mock.findByEmailAndPassword(any(), any())).then {
            PaintUser(
                it.getArgument(0),
                it.getArgument(1)
            ).toMono()
        }
    }
    private val paintUserService = PaintUserService(paintUserRepository)

    @Test
    fun `given new user when createUser() then success`() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()

        // when
        val createdUser = paintUserService.createUser(email, password).block()!!

        // then
        createdUser.email shouldBe email
        createdUser.password shouldBe password
        verify(paintUserRepository).insert(createdUser)
    }

    @Test
    fun `given exists user when signIn() then success`() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()

        // when
        StepVerifier.create(paintUserService.signIn(email, password))
            // then
            .assertNext { createdUser ->
                verify(paintUserRepository).findByEmailAndPassword(email, password)
                createdUser.email shouldBe email
                createdUser.password shouldBe password
            }
            .verifyComplete()
    }

    @Test
    fun `given not exists user when signIn() then NoSuchElementException`() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()

        whenever(paintUserRepository.findByEmailAndPassword(email, password)).then {
            Mono.empty<PaintUser>()
        }

        // when
        StepVerifier.create(paintUserService.signIn(email, password))
            // then
            .verifyErrorMatches {
                verify(paintUserRepository).findByEmailAndPassword(email, password)
                it is NoSuchElementException
            }
    }
}
