package com.hojongs.paint.repository

import com.hojongs.paint.model.PaintUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PaintUserRepository : JpaRepository<PaintUser, UUID>
