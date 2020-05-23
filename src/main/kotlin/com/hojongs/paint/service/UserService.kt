package com.hojongs.paint.service

import com.hojongs.paint.repository.model.User

interface UserService {
    fun signUp(userId: String, password: String): User

    fun signIn(userId: String, password: String): User
}