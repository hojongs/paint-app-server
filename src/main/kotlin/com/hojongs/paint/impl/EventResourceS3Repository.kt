package com.hojongs.paint.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hojongs.paint.model.PaintEvent
import com.hojongs.paint.repository.ResourceRepository
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.WritableResource
import java.nio.ByteBuffer

/**
 * @param locationPrefix example : "s3://myBucket"
 */
class EventResourceS3Repository(
    private val resourceLoader: ResourceLoader,
    private val locationPrefix: String
) : ResourceRepository<PaintEvent> {

    private val mapper = jacksonObjectMapper()

    /**
     * @param location example : "dir/file.log"
     */
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
    }

    private fun PaintEvent.getLocation(): String = this.hashCode().toString()

    private fun PaintEvent.toBytes(): ByteArray = mapper.writeValueAsBytes(this)

    private fun Resource.toEntity(): PaintEvent = run {
        val bytes = this.asByteArray()

        mapper.readValue(bytes, PaintEvent::class.java)
    }

    private fun Resource.asByteArray(): ByteArray = run {
        val byteBuffer = ByteBuffer.allocateDirect(this.contentLength().toInt())

        val readableChannel = this.readableChannel()
        readableChannel.read(byteBuffer)

        val bytes = ByteArray(this.contentLength().toInt())
        byteBuffer.get(bytes)

        bytes
    }
}
