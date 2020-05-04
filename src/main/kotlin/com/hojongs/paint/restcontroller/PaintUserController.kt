package com.hojongs.paint.restcontroller

import com.hojongs.paint.service.PaintUserService
import com.hojongs.paint.util.logger.PaintLogger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class PaintUserController(
    private val paintUserService: PaintUserService
) {
    companion object : PaintLogger()
}
