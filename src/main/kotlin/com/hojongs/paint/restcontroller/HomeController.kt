package com.hojongs.paint.restcontroller

import com.hojongs.paint.service.HomeService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class HomeController(
    private val homeService: HomeService
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    private fun home(): String =
        homeService.getMsg("user")
}
