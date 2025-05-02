package org.datenmodell.datenmodellbackend.model

import java.util.UUID as UUID

data class DataCatalog(
    val id: UUID,
    val title: String,
    val content: String,
    val dataProvider: String,
    val link: String,
)
