package com.hojongs.paint.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@ComponentScan(
    "com.hojongs.paint.config",
    "com.hojongs.paint.restcontroller",
    "com.hojongs.paint.service"
)
@EnableReactiveMongoRepositories("com.hojongs.paint.repository")
class App
