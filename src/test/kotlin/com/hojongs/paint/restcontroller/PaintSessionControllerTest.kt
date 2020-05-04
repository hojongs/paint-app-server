package com.hojongs.paint.restcontroller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hojongs.paint.model.PaintSession
import com.hojongs.paint.util.logger.PaintLogger
import io.kotlintest.shouldBe
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
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
@Suppress("UNCHECKED_CAST")
internal class PaintSessionControllerTest {
    companion object : PaintLogger() {
        const val REPEAT_COUNT = 100
    }

    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val mapper = jacksonObjectMapper()

    private val sessionInfoMap = CollectionUtils.toMultiValueMap(
        mapOf(
            "name" to listOf("test"),
            "password" to listOf("1235")
        )
    )
    private val paintSessionIds: MutableList<String> = mutableListOf()

    @RepeatedTest(REPEAT_COUNT)
    @Order(1)
    fun createOne(repetitionInfo: RepetitionInfo) {
        val idx: Int = repetitionInfo.currentRepetition - 1

        webTestClient
            .get()
            .uri { builder ->
                builder
                    .path("/sessions/create")
                    .queryParams(sessionInfoMap)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith {
                val paintSession = mapper.readValue(it.responseBody, PaintSession::class.java)

                paintSessionIds.add(paintSession.id)
                paintSession.name shouldBe sessionInfoMap["name"]!!.first()
                paintSession.password shouldBe sessionInfoMap["password"]!!.first()

                logger.info("createOne - [$idx] $paintSession")
            }
    }

    @RepeatedTest(REPEAT_COUNT)
    @Order(2)
    fun `findById - when exists then ok response`(repetitionInfo: RepetitionInfo) {
        val idx: Int = repetitionInfo.currentRepetition - 1
        val paintSessionId: String = paintSessionIds[idx]

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
                paintSession.name shouldBe sessionInfoMap["name"]!!.first()
                paintSession.password shouldBe sessionInfoMap["password"]!!.first()

                logger.info("findById - [$idx] element=$paintSession")
            }
    }

    @Test
    @Order(3)
    fun listAll() {
        val urlParamMap = CollectionUtils.toMultiValueMap(
            mapOf(
                "pageNumber" to listOf("0")
            )
        )

        webTestClient
            .get()
            .uri { builder ->
                builder
                    .path("/sessions")
                    .queryParams(urlParamMap)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith {
                val paintSessions: List<PaintSession> =
                    mapper.readValue(it.responseBody, List::class.java) as List<PaintSession>

                logger.info("listAll - size=$paintSessions.size} elements=$paintSessions")
            }
    }

    @Test
    fun `findById - when not exists then not found response`() {
        val notExistsPaintSessionId = "test-a"

        webTestClient
            .get()
            .uri("/sessions/$notExistsPaintSessionId")
            .exchange()
            .expectStatus().isNoContent
    }
}
