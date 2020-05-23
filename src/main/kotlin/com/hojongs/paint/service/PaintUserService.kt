package com.hojongs.paint.service

import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import com.hojongs.paint.repository.model.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PaintUserService(
    private val paintUserRepository: PaintUserRepository
) : UserService {

    override fun signUp(userId: String, password: String): User =
        paintUserRepository.existsById(userId)
            .let { isUserExists ->
                if (isUserExists)
                    throw Exception(isUserExists.toString())
                else
                    PaintUser(
                        email = userId,
                        displayName = userId,
                        password = password
                    )
            }
            .let { paintUser -> paintUserRepository.save(paintUser) }

    override fun signIn(userId: String, password: String): User =
        paintUserRepository
            .findByIdOrNull(userId)
            ?.takeIf { foundUser: User -> foundUser.getPassword() != password }
            ?: throw NoSuchElementException("PaintUser")
}
