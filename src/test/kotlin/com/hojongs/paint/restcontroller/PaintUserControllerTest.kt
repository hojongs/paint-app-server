package com.hojongs.paint.restcontroller

import com.hojongs.paint.app.App
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment

@AutoConfigureWebTestClient
@SpringBootTest(
    classes = [App::class],
    webEnvironment = WebEnvironment.RANDOM_PORT
)
internal class PaintUserControllerTest {
//    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
//
//    @Autowired
//    private lateinit var webTestClient: WebTestClient
//
//    private val mapper = jacksonObjectMapper()
//
//    @Test
//    fun createOne() {
//        val email = "test@example.com"
//        val password = "1234"
//
//        webTestClient
//            .get()
//            .uri { builder ->
//                val queryParams = mapOf(
//                    "email" to listOf(email),
//                    "password" to listOf(password)
//                ).toQueryParams()
//
//                builder
//                    .path("/users/create")
//                    .queryParams(queryParams)
//                    .build()
//            }
//            .exchange()
//            .expectStatus().isOk
//            .expectHeader().contentType(MediaType.APPLICATION_JSON)
//            .expectBody()
//            .consumeWith {
//                val paintUser = mapper.readValue(it.responseBody, PaintUser::class.java)
//
//                logger.debug("createOne - $paintUser")
//
//                paintUser.email shouldBe email
//                paintUser.password shouldBe password
//                paintUser.displayName shouldBe email
//            }
//    }
}
