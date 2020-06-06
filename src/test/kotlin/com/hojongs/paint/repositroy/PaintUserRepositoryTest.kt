package com.hojongs.paint.repositroy

import com.hojongs.paint.app.App
import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull


@SpringBootTest(
    classes = [App::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PaintUserRepositoryTest {

    @Autowired
    private lateinit var paintUserRepository: PaintUserRepository

    @Test
    fun temp() {
        val savedPaintUser = paintUserRepository.save(
            PaintUser(
                "ema",
                "pas",
                "dis",
                true
            )
        )

        println(savedPaintUser.displayName)

        val foundPaintUser = paintUserRepository.findByIdOrNull("ema")!!

        println(foundPaintUser.getUserId())
        println(foundPaintUser.displayName)
    }
}
