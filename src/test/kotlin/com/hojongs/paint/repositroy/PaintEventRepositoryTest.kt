package com.hojongs.paint.repositroy

import com.hojongs.paint.IntegrationTest
import com.hojongs.paint.model.PaintEvent
import com.hojongs.paint.repository.PaintEventRepository
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
internal class PaintEventRepositoryTest(
    private val paintEventRepository: PaintEventRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)
    private lateinit var testEvent: PaintEvent

    @BeforeEach
    fun setUp() {
        val event = PaintEvent(
            userId = UUID.randomUUID(),
            sessionId = UUID.randomUUID(),
            eventType = PaintEvent.Type.NONE.name
        )
        testEvent = paintEventRepository.save(event).block()!!
    }

    @AfterEach
    fun tearDown() {
        paintEventRepository.delete(testEvent)
    }

    @Test
    fun `given exists id when insert() then DuplicateKeyException error`() {
        // given
        val event = PaintEvent(
            userId = UUID.randomUUID(),
            sessionId = UUID.randomUUID(),
            eventType = PaintEvent.Type.NONE.name
        )
        val savedEvent = paintEventRepository.insert(event).block()!!

        // when
        StepVerifier.create(paintEventRepository.insert(savedEvent))
            .verifyErrorMatches { it is DuplicateKeyException }

        // tear down
        paintEventRepository.deleteById(savedEvent.id)
    }

    @RepeatedTest(10)
    fun findAll() {
        StepVerifier.create(paintEventRepository.findAll().collectList())
            .assertNext { foundEvents ->
                log.debug(foundEvents.toString())
            }
            .verifyComplete()
    }

    @Test
    fun save() {
        // given
        val event = PaintEvent(
            userId = UUID.randomUUID(),
            sessionId = UUID.randomUUID(),
            eventType = PaintEvent.Type.NONE.name
        )

        // when
        val savedEvent = paintEventRepository.save(event).block()!!

        // then
        log.debug("savedUser: $savedEvent")

        // tear down
        paintEventRepository.deleteById(savedEvent.id)
    }

    @Test
    fun findById() {
        // when
        val foundEvent = paintEventRepository.findById(testEvent.id).block()!!

        // then
        log.debug("foundUser: $foundEvent, ${foundEvent.id}")
    }
}
