package com.hojongs.paint.restcontroller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hojongs.paint.app.App
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.TestMethodOrder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
@SpringBootTest(
    classes = [App::class],
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@Suppress("UNCHECKED_CAST")
internal class PaintUserControllerTest {
    companion object {
        const val REPEAT_COUNT = 100
    }

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val mapper = jacksonObjectMapper()
}