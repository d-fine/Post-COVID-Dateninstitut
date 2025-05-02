package org.clientapp.clientappbackend.model

import java.util.UUID
import org.openapitools.model.EntityInformation

data class HubUser(
    val id: UUID,
    val username: String,
) {
  fun toResponse(): EntityInformation = EntityInformation(id = id, name = username)
}
