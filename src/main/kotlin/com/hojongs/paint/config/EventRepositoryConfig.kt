package com.hojongs.paint.config

import com.hojongs.paint.repository.EventRepository
import com.hojongs.paint.repository.ResourceRepository
import com.hojongs.paint.repository.model.PaintEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
class EventRepositoryConfig {
    @Value("\${ext.s3.event-bucket-name}")
    protected lateinit var bucketName: String

    @Bean
    fun eventResourceRepository(
        resourceLoader: ResourceLoader
    ): ResourceRepository<PaintEvent> = EventRepository(resourceLoader, bucketName)
}
