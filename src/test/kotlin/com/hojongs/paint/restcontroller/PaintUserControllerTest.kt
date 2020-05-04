package com.hojongs.paint.restcontroller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hojongs.paint.util.logger.PaintLogger
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@Suppress("UNCHECKED_CAST")
internal class PaintUserControllerTest {
    companion object : PaintLogger() {
        const val REPEAT_COUNT = 100
    }

    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val mapper = jacksonObjectMapper()
}