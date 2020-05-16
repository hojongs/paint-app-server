package com.hojongs.paint.restcontroller

import com.hojongs.paint.service.HomeService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RequestMapping
class HomeController(
    private val homeService: HomeService
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    private fun home(authentication: Authentication): String =
        homeService.getMsg(authentication.name)
}
