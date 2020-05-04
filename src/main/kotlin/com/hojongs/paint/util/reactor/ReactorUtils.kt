package com.hojongs.paint.util.reactor

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import reactor.util.retry.Retry
import java.time.Duration

object ReactorUtils {
    private val defaultRetrySpec = Retry.backoff(3, Duration.ofMillis(100))

    fun <T> mono(f: () -> T?): Mono<T> =
        Mono.create { sink -> sink.success(f()) }

    fun <T> monoOnScheduler(scheduler: Scheduler, f: () -> T?): Mono<T> =
        mono(f).subscribeOn(scheduler)

    fun <T> withRetry(mono: Mono<T>): Mono<T> =
        mono.retryWhen(defaultRetrySpec)

    fun <T> withRetry(flux: Flux<T>): Flux<T> =
        flux.retryWhen(defaultRetrySpec)
}
