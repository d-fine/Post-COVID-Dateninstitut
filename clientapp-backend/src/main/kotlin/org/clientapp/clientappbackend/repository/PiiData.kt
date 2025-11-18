package org.clientapp.clientappbackend.repository

import java.util.UUID
import org.clientapp.clientappbackend.entity.HubPiiDataEntity
import org.clientapp.clientappbackend.entity.PiiDataAttributesId
import org.clientapp.clientappbackend.entity.PiiDataBloomFilterId
import org.clientapp.clientappbackend.entity.PiiDataId
import org.clientapp.clientappbackend.entity.SatPiiDataAttributesEntity
import org.clientapp.clientappbackend.entity.SatPiiDataBloomfilterEntity
import org.clientapp.clientappbackend.entity.SatPiiDataMetadataEntity
import org.springframework.data.repository.CrudRepository

interface HubPiiDataRepository : CrudRepository<HubPiiDataEntity, UUID>

interface SatPiiDataMetadataRepository : CrudRepository<SatPiiDataMetadataEntity, PiiDataId> {
  fun findAllByValidToIsNull(): List<SatPiiDataMetadataEntity>
}

interface SatPiiDataBloomfilterRepository :
    CrudRepository<SatPiiDataBloomfilterEntity, PiiDataBloomFilterId> {
  fun findByPiiDataEntityAndValidToIsNull(
      hubPiiDataEntity: HubPiiDataEntity
  ): List<SatPiiDataBloomfilterEntity>
}


interface SatPiiDataAttributesRepository :
    CrudRepository<SatPiiDataAttributesEntity, PiiDataAttributesId> {
  fun findByPiiDataEntityAndAttributeName(
      piiDataEntity: HubPiiDataEntity,
      attributeName: String
  ): SatPiiDataAttributesEntity?

  fun findByPiiDataEntityAndValidToIsNull(
      hubPiiDataEntity: HubPiiDataEntity
  ): List<SatPiiDataAttributesEntity>


  fun existsByPiiDataEntityAndAttributeName(
      piiDataEntity: HubPiiDataEntity,
      attributeName: String
  ): Boolean
}
