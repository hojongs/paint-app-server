package com.hojongs.paint.service

import com.hojongs.paint.model.PaintUser
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class AuthenticationService(
    private val paintUserService: PaintUserService
) {
    fun authenticate(userId: UUID): Mono<PaintUser> {
        return paintUserService.getUser(userId)
    }
}
