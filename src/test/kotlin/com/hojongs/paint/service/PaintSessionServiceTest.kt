package com.hojongs.paint.service

import com.hojongs.paint.exception.AlreadyExistsException
import com.hojongs.paint.repository.PaintSessionRepository
import com.hojongs.paint.model.PaintSession
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import java.util.UUID

internal class PaintSessionServiceTest {
    private val paintSessionRepository = mock<PaintSessionRepository>()
    private val paintSessionService = spy(PaintSessionService(paintSessionRepository))

    @Test
    fun `given not exists session name when createSession() then success`() {
        // given
        val userId = UUID.randomUUID()
        val name = "sess"
        val password = "pas"
        given(paintSessionRepository.findByName(name))
            .will { Mono.empty<PaintSession>() }
        given(paintSessionRepository.save(any<PaintSession>()))
            .will { PaintSession(userId = userId, name = name, password = password).toMono() }

        // when
        StepVerifier.create(paintSessionService.createSession(userId, name, password))
            // then
            .assertNext {
                it.userId shouldBe userId
                it.name shouldBe name
                it.password shouldBe password
            }
            .verifyComplete()
    }

    @Test
    fun `given exists session name when createSession() then AlreadyExistsException error`() {
        // given
        val userId = UUID.randomUUID()
        val name = "sess"
        val password = "pas"
        given(paintSessionRepository.findByName(name))
            .will {
                val nameDuplicatedSession = PaintSession(userId = UUID.randomUUID(), name = name, password = "pas2")
                nameDuplicatedSession.toMono()
            }
        given(paintSessionRepository.save(any<PaintSession>()))
            .will { Mono.empty<PaintSession>() }

        // when
        StepVerifier.create(paintSessionService.createSession(userId, name, password))
            // then
            .verifyErrorMatches { it is AlreadyExistsException && it.key == name }
    }

    @Test
    fun `given 100 sessions when listSession() then return 100 sessions`() {
        val userIds = listOf(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID()
        )

        given(paintSessionRepository.findAll())
            .will {
                IntRange(0, 99).map { i ->
                    PaintSession(
                        userId = userIds.random(),
                        name = "sess$i",
                        password = "pas"
                    )
                }.toFlux()
            }

        // when
        StepVerifier.create(paintSessionService.listSession().log())
            // then
            .expectNextCount(100)
            .verifyComplete()
    }
}
