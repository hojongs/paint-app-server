package com.hojongs.paint.service

import com.hojongs.paint.repository.PaintUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PaintUserService(
    private val paintUserRepository: PaintUserRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
}
