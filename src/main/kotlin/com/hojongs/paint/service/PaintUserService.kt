package com.hojongs.paint.service

import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.util.logger.PaintLogger
import org.springframework.stereotype.Service
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers

@Service
class PaintUserService(
    private val paintUserRepository: PaintUserRepository,
    private val ioScheduler: Scheduler = Schedulers.boundedElastic()
) {
    companion object : PaintLogger()
}
