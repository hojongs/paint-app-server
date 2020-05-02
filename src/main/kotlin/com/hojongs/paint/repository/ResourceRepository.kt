package com.hojongs.paint.repository

interface ResourceRepository<T> {
    fun findByLocation(location: String): T
    fun save(resource: T)
}
