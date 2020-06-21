package com.hojongs.paint.repository

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hojongs.paint.model.PaintEvent
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.WritableResource
import org.springframework.stereotype.Repository
import java.nio.ByteBuffer

@Repository
class EventResourceRepository(
    private val resourceLoader: ResourceLoader,
    bucketName: String
) : ResourceRepository<PaintEvent> {

    private val locationPrefix: String = "s3://$bucketName/"
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val mapper = jacksonObjectMapper()

    override fun findByLocation(
        location: String
    ): PaintEvent =
        resourceLoader.getResource("$locationPrefix$location")
            .let { resource ->
                if (!resource.exists()) throw NoSuchElementException("Resource")
                else resource.read()
            }
            .let { bytes -> mapper.readValue(bytes, PaintEvent::class.java) }

    override fun save(location: String, entity: PaintEvent): PaintEvent = run {
        "$locationPrefix$location"
            .let { location ->
                logger.debug("location=${location}")

                resourceLoader.getResource(location)
            }
            .let { resource ->
                if (resource !is WritableResource)
                    throw IllegalArgumentException("location=$location")

                resource.writableChannel()
            }
            .let { writableByteChannel ->
                writableByteChannel.write(
                    entity.toByteBuffer()
                )
                writableByteChannel.close()
            }
            .let { entity }
    }

    private fun PaintEvent.toByteBuffer(): ByteBuffer =
        this.let { mapper.writeValueAsBytes(it) }
            .let { bytes -> ByteBuffer.wrap(bytes) }

    private fun Resource.read(): ByteArray = run {
        val size = this.contentLength()
        val byteBuffer = ByteBuffer.allocateDirect(size.toInt())

        val readableChannel = this.readableChannel()
        readableChannel.read(byteBuffer)

        byteBuffer.flip() // set position to read
        val bytes = ByteArray(this.contentLength().toInt())
        byteBuffer.get(bytes)

        bytes
    }
}
