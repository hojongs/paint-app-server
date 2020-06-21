package com.hojongs.paint.repositroy

import com.hojongs.paint.IntegrationTest
import com.hojongs.paint.repository.PaintSessionRepository
import com.hojongs.paint.model.PaintSession
import io.kotlintest.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import reactor.test.StepVerifier
import java.util.UUID

// docker run --rm --name mongo -p 27017:27017 -d mongo:4.2.8
@IntegrationTest
internal class PaintSessionRepositoryTest(
    private val paintSessionRepository: PaintSessionRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)
    private lateinit var testSession: PaintSession

    @BeforeEach
    fun setUp() {
        val session = PaintSession(userId = UUID.randomUUID(), name = "sess", password = "pas")
        testSession = paintSessionRepository.save(session).block()!!
    }

    @AfterEach
    fun tearDown() {
        paintSessionRepository.delete(testSession)
    }

    @Test
    fun `given exists id when insert() then DuplicateKeyException error`() {
        // given
        val session = PaintSession(userId = UUID.randomUUID(), name = "sess", password = "pas")
        val savedSession = paintSessionRepository.insert(session).block()!!

        // when
        StepVerifier.create(paintSessionRepository.insert(savedSession))
            .verifyErrorMatches { it is DuplicateKeyException }

        // tear down
        paintSessionRepository.deleteById(savedSession.id)
    }

    @RepeatedTest(10)
    fun findAll() {
        StepVerifier.create(paintSessionRepository.findAll().collectList())
            .assertNext { foundUsers ->
                log.debug(foundUsers.toString())
            }
            .verifyComplete()
    }

    @Test
    fun save() {
        // given
        val session = PaintSession(userId = UUID.randomUUID(), name = "sess", password = "pas")

        // when
        StepVerifier.create(paintSessionRepository.save(session))
            // then
            .assertNext { savedSession ->
                savedSession::class shouldBe PaintSession::class
                log.debug("savedSession: $savedSession")

                // tear down
                paintSessionRepository.deleteById(savedSession.id)
            }
            .verifyComplete()
    }

    @Test
    fun findById() {
        // when
        StepVerifier.create(paintSessionRepository.findById(testSession.id))
            // then
            .assertNext {foundSession ->
                foundSession::class shouldBe PaintSession::class
                log.debug("foundSession: $foundSession")
            }
            .verifyComplete()
    }
}
