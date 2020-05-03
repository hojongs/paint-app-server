package com.hojongs.paint.util.reactor

import reactor.core.publisher.Mono

fun <T> monoFromCallable(f: () -> T?): Mono<T> =
    Mono.create { sink -> sink.success(f()) }
