package com.hojongs.paint.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@ComponentScan(
    "com.hojongs.paint.restcontroller",
    "com.hojongs.paint.service"
)
@EnableWebSecurity
@EnableJpaRepositories("com.hojongs.paint.repository")
@EntityScan("com.hojongs.paint.repository.model")
class App
