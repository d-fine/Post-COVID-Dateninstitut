package org.datenmodell.datenmodellbackend.repository

import java.util.UUID
import org.datenmodell.datenmodellbackend.entities.DataAnalysisEntity
import org.datenmodell.datenmodellbackend.entities.DataAnalysisMetaDataEntity
import org.datenmodell.datenmodellbackend.entities.DataAnalysisMetaDataId
import org.datenmodell.datenmodellbackend.service.db.HubSatTableRepositoryWrapper
import org.springframework.data.repository.CrudRepository

interface DataAnalysisRepository : CrudRepository<DataAnalysisEntity, UUID>

interface DataAnalysisMetaDataRepository :
    CrudRepository<DataAnalysisMetaDataEntity, DataAnalysisMetaDataId> {
  fun findByValidToIsNull(): List<DataAnalysisMetaDataEntity>

  fun findByDataAnalysisEntityAndValidToIsNull(
      dataAnalysisEntity: DataAnalysisEntity?
  ): List<DataAnalysisMetaDataEntity>
}

class DataAnalysisHubSatAccess(
    override val hubRepository: DataAnalysisRepository,
    override val satRepository: DataAnalysisMetaDataRepository
) :
    HubSatTableRepositoryWrapper<
        DataAnalysisEntity, DataAnalysisMetaDataEntity, DataAnalysisMetaDataId> {
  override val satRepositoryTableName: String
    get() = "data_analysis"

  override fun getValidRecord(hubEntity: DataAnalysisEntity?): List<DataAnalysisMetaDataEntity> {
    return satRepository.findByDataAnalysisEntityAndValidToIsNull(hubEntity)
  }
}
