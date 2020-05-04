package com.hojongs.paint.util.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class PaintLogger {
    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)
}
