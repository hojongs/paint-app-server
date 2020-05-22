package com.hojongs.paint.repositroy

import com.hojongs.paint.app.App
import com.hojongs.paint.repository.ResourceRepository
import com.hojongs.paint.repository.model.PaintEvent
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [App::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
internal class EventResourceRepositoryTest {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var eventResourceRepository: ResourceRepository<PaintEvent>

    @Test
    fun save() {
        val paintEvent = PaintEvent(
            "release"
        )

        eventResourceRepository.save(paintEvent)
    }

    @Test
    fun findByLocation() {
        val paintEvent = eventResourceRepository.findByLocation("1090594823")

        logger.info("paintEvent: $paintEvent")
    }
}
