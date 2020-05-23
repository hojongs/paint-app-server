package com.hojongs.paint.repository

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hojongs.paint.repository.model.PaintEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.WritableResource
import org.springframework.stereotype.Repository
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Paths

@Repository
class EventResourceRepository(
    private val resourceLoader: ResourceLoader,
    @Value("\${ext.resource-location-prefix}")
    private val locationPrefix: String
) : ResourceRepository<PaintEvent> {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val mapper = jacksonObjectMapper()

    override fun findByLocation(
        location: String
    ): PaintEvent =
        resourceLoader.getResource("$locationPrefix$location")
            .let { resource ->
                if (!resource.exists()) throw NoSuchElementException("Resource")
                else resource.asByteArray()
            }
            .let { bytes -> mapper.readValue(bytes, PaintEvent::class.java) }

    override fun save(location: String, resource: PaintEvent): PaintEvent = run {
        "$locationPrefix$location"
            .also { location -> logger.debug("location=${location}") }
            .let { location -> resourceLoader.getResource(location) }
            .let { resource ->
                when (resource) {
                    is FileSystemResource ->
                        if (resource.exists())
                            resource.writableChannel()
                        else
                            FileChannel.open(
                                Paths.get(location)
                            )
                    is WritableResource ->
                        resource.writableChannel()
                    else ->
                        throw NotImplementedError("${resource::class}")
                }
            }
            .let { writableByteChannel ->
                writableByteChannel.write(
                    resource.toBytes()
                        .let { bytes -> ByteBuffer.wrap(bytes) }
                )
                writableByteChannel.close()
            }
            .let { resource }
    }

    private fun PaintEvent.toBytes(): ByteArray = mapper.writeValueAsBytes(this)

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
