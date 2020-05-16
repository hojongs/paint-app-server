package com.hojongs.paint.configuration

import com.hojongs.paint.restcontroller.HomeController
import com.hojongs.paint.service.HomeService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HomeControllerConfig {

    @Bean
    fun homeController() = HomeController(homeService())

    @Bean
    fun homeService() = HomeService()
}
