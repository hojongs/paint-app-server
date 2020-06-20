package com.hojongs.paint.exception

import java.util.UUID

class AlreadyExistsException(id: UUID) : Exception("already exists id=$id")
