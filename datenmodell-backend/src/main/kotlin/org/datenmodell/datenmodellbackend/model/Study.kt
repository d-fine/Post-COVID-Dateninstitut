package org.datenmodell.datenmodellbackend.model

import java.util.UUID

data class Study(
    val id: UUID,
    val title: String,
    val content: String,
)
