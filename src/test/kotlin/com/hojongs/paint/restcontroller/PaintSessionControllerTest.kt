package com.hojongs.paint.restcontroller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hojongs.paint.model.PaintSession
import io.kotlintest.shouldBe
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.CollectionUtils

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PaintSessionControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val logger = LoggerFactory.getLogger(javaClass)
    private val mapper = jacksonObjectMapper()

    private val urlParamMap = CollectionUtils.toMultiValueMap(
        mapOf(
            "name" to listOf("test"),
            "password" to listOf("1235")
        )
    )
    private lateinit var paintSessionId: String

    @Test
    @Order(1)
    fun createOne() {
        webTestClient
            .get()
            .uri {
                it
                    .path("/sessions/create")
                    .queryParams(urlParamMap)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith {
                val paintSession = mapper.readValue(it.responseBody, PaintSession::class.java)

                paintSessionId = paintSession.id
                paintSession.name shouldBe urlParamMap["name"]!!.first()
                paintSession.password shouldBe urlParamMap["password"]!!.first()

                logger.info("createOne - $paintSession")
            }
    }

    @Test
    @Order(2)
    fun `findById - when exists then ok response`() {
        webTestClient
            .get()
            .uri("/sessions/$paintSessionId")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith {
                val paintSession = mapper.readValue(it.responseBody, PaintSession::class.java)

                paintSession.id shouldBe paintSessionId
                paintSession.name shouldBe urlParamMap["name"]!!.first()
                paintSession.password shouldBe urlParamMap["password"]!!.first()

                logger.info("findById - $paintSession")
            }
    }

    @Test
    fun `findById - when not exists then not found response`() {
        val notExistsPaintSessionId = "test-a"

        webTestClient
            .get()
            .uri("/sessions/$notExistsPaintSessionId")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentLength(0)
    }
}
