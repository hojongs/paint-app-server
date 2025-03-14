package com.hojongs.paint.repositroy

import com.hojongs.paint.IntegrationTest
import com.hojongs.paint.repository.ResourceRepository
import com.hojongs.paint.model.PaintEvent
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import java.util.UUID
import kotlin.random.Random

@IntegrationTest
@Disabled
internal class EventResourceRepositoryTest(
    private val eventResourceRepository: ResourceRepository<PaintEvent>
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val location = "temp-${Random.nextLong(0, Long.MAX_VALUE)}.txt"

    @Test
    fun `save - success`() {
        val paintEvent = PaintEvent(
            userId = UUID.randomUUID(),
            sessionId = UUID.randomUUID(),
            eventType = PaintEvent.Type.NONE.name
        )

        eventResourceRepository.save(location, paintEvent)
    }

    @Test
    fun `findByLocation - When Not Exists Then NoSuchElementException error`() {
        assertThrows<NoSuchElementException> {
            val paintEvent = eventResourceRepository.findByLocation(location)

            logger.debug("paintEvent: $paintEvent")
        }
    }
}
