package com.hojongs.paint.exception

class NotExistsException(val key: Any, cause: Throwable? = null) : Exception("not exists (key=$key)", cause)