package com.hojongs.paint.configuration

import com.hojongs.paint.repository.EventResourceRepository
import com.hojongs.paint.restcontroller.HomeController
import com.hojongs.paint.service.HomeService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
class EventControllerConfig {
    @Value("\${ext.resource-location-prefix}")
    private lateinit var locationPrefix: String

    @Bean
    fun eventResourceRepository(
        resourceLoader: ResourceLoader
    ) = EventResourceRepository(resourceLoader, locationPrefix)
}
