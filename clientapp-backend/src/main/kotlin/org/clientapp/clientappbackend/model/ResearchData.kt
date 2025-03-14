package org.clientapp.clientappbackend.model

import java.time.LocalDateTime
import java.util.UUID

import org.openapitools.model.EntityInformation

data class HubResearchData(
    val id: UUID,
    val user_id: UUID,
)

data class SatResearchDataAttributes(
    val research_data_id: UUID,
    val valid_from: LocalDateTime = LocalDateTime.now(),
    val valid_to: LocalDateTime? = null,
    val name: String,
    val description: String,
    val file: ByteArray,
    val status: String,
    val user_id: UUID
)

data class ResearchDataSet(
    val file: ByteArray,
    val name: String
)
