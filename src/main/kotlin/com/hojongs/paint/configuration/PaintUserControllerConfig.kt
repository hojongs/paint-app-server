package com.hojongs.paint.configuration

import com.hojongs.paint.service.PaintUserService
import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.restcontroller.PaintUserController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaintUserControllerConfig {

    @Autowired
    private lateinit var paintUserRepository: PaintUserRepository

    @Bean
    fun paintUserController() = PaintUserController(paintUserService())

    @Bean
    fun paintUserService() = PaintUserService(paintUserRepository)
}