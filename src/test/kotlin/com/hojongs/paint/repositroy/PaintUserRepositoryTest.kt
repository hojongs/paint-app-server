package com.hojongs.paint.repositroy

import com.hojongs.paint.IntegrationTest
import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.model.PaintUser
import io.kotlintest.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.UUID

// docker run --rm --name mongo -p 27017:27017 -d mongo:4.2.8
@IntegrationTest
internal class PaintUserRepositoryTest(
    private val paintUserRepository: PaintUserRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)
    private lateinit var savedUser: PaintUser

    @BeforeEach
    fun setUp() {
        val user = PaintUser(email = "ema", password = "pas")
        savedUser = paintUserRepository.save(user).block()!!
    }

    @AfterEach
    fun tearDown() {
        paintUserRepository.delete(savedUser)
    }

    @Test
    fun `given exists id when insert() then DuplicateKeyException error`() {
        // given
        val user = PaintUser(email = "ema", password = "pas")
        val savedUser = paintUserRepository.insert(user).block()!!

        // when
        StepVerifier.create(paintUserRepository.insert(savedUser))
            .verifyErrorMatches { it is DuplicateKeyException }

        // tear down
        paintUserRepository.deleteById(savedUser.id)
    }

    @RepeatedTest(10)
    fun findAll() {
        StepVerifier.create(paintUserRepository.findAll().collectList())
            .assertNext { foundUsers ->
                log.debug(foundUsers.toString())
            }
            .verifyComplete()
    }

    @Test
    fun save() {
        // given
        val user = PaintUser(email = "ema", password = "pas")

        // when
        val savedUser = paintUserRepository.save(user).block()!!

        // then
        savedUser::class shouldBe PaintUser::class
        log.debug("savedUser: $savedUser")

        // tear down
        paintUserRepository.deleteById(savedUser.id)
    }

    @Test
    fun findById() {
        // when
        val foundUser = paintUserRepository.findById(savedUser.id).block()!!

        // then
        foundUser::class shouldBe PaintUser::class
        log.debug("foundUser: $foundUser")
    }

    @Test
    fun findByJoinedSessionId() {
        // given
        val sessionId = UUID.randomUUID()
        val joinedUsers = IntRange(0, 3).map {
            PaintUser(
                email = "ema$it",
                password = "pas",
                joinedSessionId = sessionId
            )
        }
        val notJoinedUsers = listOf(
            PaintUser(
                email = "emaa",
                password = "pas",
                joinedSessionId = UUID.randomUUID()
            ),
            PaintUser(
                email = "emaa",
                password = "pas",
                joinedSessionId = null
            )
        )
        val savedUsers = paintUserRepository
            .saveAll(joinedUsers + notJoinedUsers)
            .collectList()
            .block()!!

        // when
        StepVerifier.create(paintUserRepository.findByJoinedSessionId(sessionId).collectList())
            // then
            .assertNext {
                it.size shouldBe 4

                // tear down
                paintUserRepository.deleteAll(savedUsers)
            }
            .verifyComplete()
    }
}
