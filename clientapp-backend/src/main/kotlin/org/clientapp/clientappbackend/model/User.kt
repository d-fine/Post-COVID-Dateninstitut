package org.clientapp.clientappbackend.model

import java.util.UUID
import java.time.LocalDateTime

import org.openapitools.model.EntityInformation


data class HubUser(
    val id: UUID,
    val username: String,
) {
    fun toResponse(): EntityInformation = EntityInformation(id = id, name = username)
}

data class SatUserMetaData(
    val user_id: UUID,
    val valid_from: LocalDateTime = LocalDateTime.now(),
    val valid_to: LocalDateTime? = null,
    val first_name: String,
    val surname: String,
)
