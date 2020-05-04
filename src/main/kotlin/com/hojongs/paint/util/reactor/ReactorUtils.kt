package com.hojongs.paint.util.reactor

import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler

object ReactorUtils {
    fun <T> mono(f: () -> T?): Mono<T> =
        Mono.create { sink -> sink.success(f()) }

    fun <T> monoOnScheduler(scheduler: Scheduler, f: () -> T?): Mono<T> =
        mono(f).subscribeOn(scheduler)
}
