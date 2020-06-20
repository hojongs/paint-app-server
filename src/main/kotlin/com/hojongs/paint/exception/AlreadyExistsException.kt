package com.hojongs.paint.exception

class AlreadyExistsException(val key: Any, cause: Throwable? = null) : Exception("already exists key=$key", cause)
