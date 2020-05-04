package com.hojongs.paint.util.reactor

import reactor.core.publisher.Mono

object ReactorUtils {
    fun <T> mono(f: () -> T?): Mono<T> =
        Mono.create { sink -> sink.success(f()) }
}
