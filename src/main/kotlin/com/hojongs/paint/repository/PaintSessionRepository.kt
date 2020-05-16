package com.hojongs.paint.repository

import com.hojongs.paint.repository.model.PaintSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaintSessionRepository : JpaRepository<PaintSession, String>
