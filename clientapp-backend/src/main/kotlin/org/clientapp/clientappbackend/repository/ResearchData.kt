package org.clientapp.clientappbackend.repository

import java.util.UUID
import org.clientapp.clientappbackend.entity.HubResearchDataEntity
import org.clientapp.clientappbackend.entity.ResearchDataMetaDataId
import org.clientapp.clientappbackend.entity.SatResearchDataMetaDataEntity
import org.clientapp.clientappbackend.service.db.HubSatTableRepositoryWrapper
import org.springframework.data.repository.CrudRepository

interface HubResearchDataRepository : CrudRepository<HubResearchDataEntity, UUID>

interface SatResearchDataMetaDataRepository :
    CrudRepository<SatResearchDataMetaDataEntity, ResearchDataMetaDataId> {
  fun findAllByValidToIsNull(): List<SatResearchDataMetaDataEntity>

  fun findByResearchDataEntityAndValidToIsNull(
      researchDataEntity: HubResearchDataEntity?,
  ): List<SatResearchDataMetaDataEntity>
}

class ResearchDataHubSatRepositoryWrapper(
    override val hubRepository: HubResearchDataRepository,
    override val satRepository: SatResearchDataMetaDataRepository
) :
    HubSatTableRepositoryWrapper<
        HubResearchDataEntity, SatResearchDataMetaDataEntity, ResearchDataMetaDataId> {
  override val satRepositoryTableName: String
    get() = "transaction"

  override fun getValidRecord(
      hubEntity: HubResearchDataEntity?
  ): List<SatResearchDataMetaDataEntity> =
      satRepository.findByResearchDataEntityAndValidToIsNull(hubEntity)
}
