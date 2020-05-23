package com.hojongs.paint.repository

interface ResourceRepository<Entity> {
    fun findByLocation(location: String): Entity
    fun save(location: String, entity: Entity): Entity
}
