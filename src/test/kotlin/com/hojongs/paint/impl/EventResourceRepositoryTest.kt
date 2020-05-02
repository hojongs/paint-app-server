package com.hojongs.paint.impl

import com.hojongs.paint.model.PaintEvent
import com.hojongs.paint.repository.ResourceRepository
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
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
