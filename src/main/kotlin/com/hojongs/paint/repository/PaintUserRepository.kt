package com.hojongs.paint.repository

import com.hojongs.paint.repository.model.PaintUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaintUserRepository : JpaRepository<PaintUser, String>
