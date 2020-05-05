package com.hojongs.paint.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class HomeService {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PreAuthorize("hasRole('ADMIN')")
    fun getMsg() = "hello"
}
