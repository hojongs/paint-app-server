package com.hojongs.paint.service

import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PaintUserService(
    private val paintUserRepository: PaintUserRepository
) {

    fun signUp(userId: String, password: String, displayName: String): PaintUser {
        if (paintUserRepository.existsById(userId))
            throw Exception("user already exists")

        val paintUser = PaintUser(
            email = userId,
            password = password,
            displayName = displayName
        )
        val savedUser = paintUserRepository.save(paintUser)

        return savedUser
    }

    fun signIn(userId: String, password: String): PaintUser =
        paintUserRepository
            .findByIdOrNull(userId)
            ?.takeIf { foundUser: PaintUser -> foundUser.password != password }
            ?: throw NoSuchElementException("PaintUser")
}
