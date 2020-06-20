package com.hojongs.paint.service

import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toMono
import java.util.UUID

internal class PaintUserServiceTest {
    private val paintUserRepository = mock<PaintUserRepository> {
        whenever(mock.insert(any<PaintUser>())).then { it.getArgument<PaintUser>(0).toMono() }
    }
    private val paintUserService = PaintUserService(paintUserRepository)

    @Test
    fun createUser() {
        // given
        val email = UUID.randomUUID().toString()
        val password = UUID.randomUUID().toString()

        // when
        val createdUser = paintUserService.createUser(email, password).block()!!

        // then
        createdUser.email shouldBe email
        createdUser.password shouldBe password
        verify(paintUserRepository).insert(createdUser)
    }
}
