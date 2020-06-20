package com.hojongs.paint.repositroy

import com.hojongs.paint.IntegrationTest
import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import io.kotlintest.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

// docker run --rm --name mongo -p 27017:27017 -d mongo:4.2.8
@IntegrationTest
internal class PaintUserRepositoryTest(
    private val paintUserRepository: PaintUserRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)
    private lateinit var savedUser: PaintUser

    @BeforeEach
    fun setUp() {
        val user = PaintUser("ema", "pas")
        savedUser = paintUserRepository.save(user).block()!!
    }

    @AfterEach
    fun tearDown() {
        paintUserRepository.delete(savedUser)
    }

    @Test
    fun save() {
        // given
        val user = PaintUser("ema", "pas")

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
}
