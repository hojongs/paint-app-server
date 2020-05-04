package com.hojongs.paint.util.logger

import org.slf4j.Logger
import reactor.core.publisher.Mono

object LoggerUtils {
    fun <T> logError(logger: Logger, mono: Mono<T>): Mono<T> =
        mono.doOnError { err ->
            logger.error(err.message, err)
        }
}
