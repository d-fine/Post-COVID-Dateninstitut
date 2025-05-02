package org.datenmodell.datenmodellbackend.service.db

import java.time.OffsetDateTime
import java.util.UUID
import org.datenmodell.datenmodellbackend.entities.DataAnalysisEntity
import org.datenmodell.datenmodellbackend.entities.DataAnalysisMetaDataEntity
import org.datenmodell.datenmodellbackend.repository.DataAnalysisHubSatAccess
import org.datenmodell.datenmodellbackend.repository.DataAnalysisMetaDataRepository
import org.datenmodell.datenmodellbackend.repository.DataAnalysisRepository
import org.openapitools.model.DataAnalysis
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DataAnalysisTableService(
    @Autowired private val dataAnalysisRepository: DataAnalysisRepository,
    @Autowired private val dataAnalysisMetaDataRepository: DataAnalysisMetaDataRepository,
    private val dataAnalysisAccess: DataAnalysisHubSatAccess =
        DataAnalysisHubSatAccess(
            dataAnalysisRepository,
            dataAnalysisMetaDataRepository,
        )
) {

  fun getAllValidDataAnalysisApps(): List<DataAnalysis> {
    val apps = dataAnalysisMetaDataRepository.findByValidToIsNull()
    return apps.map {
      DataAnalysis(
          id = it.dataAnalysisEntity.id!!,
          description = it.description,
          name = it.dataAnalysisEntity.appName)
    }
  }

  private fun createDataAnalysis(dataAnalysisEntity: DataAnalysisEntity): DataAnalysisEntity =
      dataAnalysisRepository.save(dataAnalysisEntity)

  fun createMetaDataAnalysis(
      appName: String,
      description: String,
      ddlStatement: String,
      imageLocation: String,
  ): DataAnalysisMetaDataEntity {
    val dataAnalysisEntity = DataAnalysisEntity(appName)
    createDataAnalysis(dataAnalysisEntity)

    val dataAnalysisMetaDataEntity =
        DataAnalysisMetaDataEntity(
            dataAnalysisEntity,
            OffsetDateTime.now(),
            description = description,
            ddlStatement = ddlStatement,
            imageLocation = imageLocation,
        )

    return dataAnalysisMetaDataRepository.save(dataAnalysisMetaDataEntity)
  }

  fun getValidDataAnalysisMetaData(id: UUID): DataAnalysisMetaDataEntity {
    return dataAnalysisAccess.getValidRecord(id)
  }
}
