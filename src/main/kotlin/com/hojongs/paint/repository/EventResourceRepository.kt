package com.hojongs.paint.repository

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hojongs.paint.repository.model.PaintEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.WritableResource
import java.nio.ByteBuffer

class EventResourceRepository(
    private val resourceLoader: ResourceLoader,

    @Value("\${ext.resource-location-prefix}")
    private val locationPrefix: String
) : ResourceRepository<PaintEvent> {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val mapper = jacksonObjectMapper()

    override fun findByLocation(
        location: String
    ): PaintEvent = run {
        val resource: Resource = resourceLoader.getResource("$locationPrefix/$location")

        resource.toEntity()
    }

    override fun save(resource: PaintEvent): Unit = run {
        val writableChannel = resource.getLocation()
            .let { location -> resourceLoader.getResource("$locationPrefix/$location") as WritableResource }.writableChannel()

        resource.toBytes()
            .let { bytes -> ByteBuffer.wrap(bytes) }
            .let { byteBuffer -> writableChannel.write(byteBuffer) }
            .let { writableChannel.close() }
    }

    private fun PaintEvent.getLocation(): String = this.hashCode().toString()

    private fun PaintEvent.toBytes(): ByteArray = mapper.writeValueAsBytes(this)

    private fun Resource.toEntity(): PaintEvent = run {
        val bytes = this.asByteArray()

        mapper.readValue(bytes, PaintEvent::class.java)
    }

    private fun Resource.asByteArray(): ByteArray = run {
        val size = this.contentLength()
        val byteBuffer = ByteBuffer.allocateDirect(size.toInt())

        val readableChannel = this.readableChannel()
        readableChannel.read(byteBuffer)

        byteBuffer.flip() // set position & limit to read
        val bytes = ByteArray(this.contentLength().toInt())
        byteBuffer.get(bytes)

        bytes
    }
}
