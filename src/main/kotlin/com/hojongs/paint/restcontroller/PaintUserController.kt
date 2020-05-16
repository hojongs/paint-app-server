package com.hojongs.paint.restcontroller

import com.hojongs.paint.service.PaintUserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class PaintUserController(
    private val paintUserService: PaintUserService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
}
