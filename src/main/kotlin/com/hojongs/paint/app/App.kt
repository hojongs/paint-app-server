package com.hojongs.paint.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(
    "com.hojongs.paint.config",
    "com.hojongs.paint.restcontroller",
    "com.hojongs.paint.service"
)
@EnableJpaRepositories("com.hojongs.paint.repository")
@EntityScan("com.hojongs.paint.repository.model")
class App
