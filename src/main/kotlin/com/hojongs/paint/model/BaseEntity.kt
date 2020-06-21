package com.hojongs.paint.model

open class BaseEntity {
    // todo check performance
    override fun toString(): String {
        val className = javaClass.simpleName
        val fields = javaClass.declaredFields.map {
            it.isAccessible = true
            val fieldName = it.name
            val fieldValue = it.get(this)
            "$fieldName=$fieldValue"
        }
        return "$className($fields)"
    }
}
