package com.hojongs.paint.configuration

import com.hojongs.paint.service.PaintSessionService
import com.hojongs.paint.repository.PaintSessionRepository
import com.hojongs.paint.restcontroller.PaintSessionController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaintSessionControllerConfig {

    @Autowired
    private lateinit var paintSessionRepository: PaintSessionRepository

    @Bean
    fun paintSessionController() = PaintSessionController(paintSessionService())

    @Bean
    fun paintSessionService() = PaintSessionService(paintSessionRepository)
}
