package org.datenmodell.datenmodellbackend.service.db

import java.time.OffsetDateTime
import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface TimeBoundMetaDataEntity {
  fun copy(): TimeBoundMetaDataEntity

  var validFrom: OffsetDateTime
  var validTo: OffsetDateTime?
}

/**
 * A class for accessing data that is managed as a hub table and a corresponding satellite table
 * with metadata
 */
// HubSatTableRepositoryWrapper
interface HubSatTableRepositoryWrapper<Hub, Sat : TimeBoundMetaDataEntity, SatID> {
  val hubRepository: CrudRepository<Hub, UUID>
  val satRepository: CrudRepository<Sat, SatID>

  val satRepositoryTableName: String
    get() = "unknown"

  fun getValidRecord(hubEntity: Hub?): List<Sat>

  @Suppress("kotlin:S6530")
  fun invalidateCurrentSatEntity(id: UUID): Sat {
    var oldEntity = getValidRecord(id)
    var newEntity = oldEntity.copy()
    val currentTimestamp = OffsetDateTime.now()
    oldEntity.validTo = currentTimestamp
    satRepository.save(oldEntity)
    newEntity.validFrom = currentTimestamp
    return newEntity as Sat
  }

  fun getValidRecord(id: UUID): Sat {
    val hubEntity = hubRepository.findById(id).orElse(null)
    return getCurrentValidRecord(hubEntity)
  }

  fun getCurrentValidRecord(hubEntity: Hub?): Sat {
    val candidates = getValidRecord(hubEntity)
    when {
      candidates.size > 1 ->
          error(
              "The database has ill-defined entries:" +
                  "It contains more than one temporally valid record")
      candidates.isEmpty() ->
          error("The table $satRepositoryTableName does not have the requested entry")
      else -> return candidates.first()
    }
  }
}
