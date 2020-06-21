package com.hojongs.paint.exception

import java.util.UUID

class NotJoinedSessionException(userId: UUID) : Exception("user not joined session")
