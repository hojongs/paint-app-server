package com.hojongs.paint.repositroy

import com.hojongs.paint.app.App
import com.hojongs.paint.repository.ResourceRepository
import com.hojongs.paint.repository.model.PaintEvent
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    "ext.resource-location-prefix=file:",
    classes = [App::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
internal class EventResourceRepositoryTest {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var eventResourceRepository: ResourceRepository<PaintEvent>
    private val location = "temp.txt"

    @Test
    fun `save - success`() {
        val paintEvent = PaintEvent(
            "release"
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
