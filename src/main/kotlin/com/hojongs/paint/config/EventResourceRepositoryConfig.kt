package com.hojongs.paint.config

import com.hojongs.paint.repository.EventResourceRepository
import com.hojongs.paint.repository.ResourceRepository
import com.hojongs.paint.repository.model.PaintEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
class EventResourceRepositoryConfig {
    @Value("\${ext.resource-location-prefix}")
    protected lateinit var locationPrefix: String

    @Bean
    fun eventResourceRepository(
        resourceLoader: ResourceLoader
    ): ResourceRepository<PaintEvent> = EventResourceRepository(
        resourceLoader,
        locationPrefix
    )
}