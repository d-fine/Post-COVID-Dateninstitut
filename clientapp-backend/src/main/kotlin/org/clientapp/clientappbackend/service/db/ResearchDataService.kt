package org.clientapp.clientappbackend.service.db

import db.migration.ApprovalStatus
import java.time.LocalDateTime
import java.util.UUID
import kotlin.jvm.optionals.getOrNull
import org.clientapp.clientappbackend.entity.HubResearchDataEntity
import org.clientapp.clientappbackend.entity.SatResearchDataMetaDataEntity
import org.clientapp.clientappbackend.model.ResearchDataSet
import org.clientapp.clientappbackend.repository.HubResearchDataRepository
import org.clientapp.clientappbackend.repository.ResearchDataHubSatRepositoryWrapper
import org.clientapp.clientappbackend.repository.SatResearchDataMetaDataRepository
import org.openapitools.model.EntityInformation
import org.openapitools.model.ResearchDataInformation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResearchDataService(
    @Autowired private val hubResearchDataRepository: HubResearchDataRepository,
    @Autowired private val satResearchDataMetaDataRepository: SatResearchDataMetaDataRepository,
    private val researchDataAccess: ResearchDataHubSatRepositoryWrapper =
        ResearchDataHubSatRepositoryWrapper(
            hubResearchDataRepository,
            satResearchDataMetaDataRepository,
        )
) {

  fun getHubResearchDataEntity(researchDataId: UUID): HubResearchDataEntity? =
      hubResearchDataRepository.findById(researchDataId).getOrNull()

  fun getResearchDataSet(researchDataId: UUID): ResearchDataSet {
    val datasets = researchDataAccess.getValidRecord(researchDataId)
    return ResearchDataSet(
        file = datasets.file,
        name = datasets.name,
    )
  }

  fun getAllResearchData(): List<ResearchDataInformation> =
      satResearchDataMetaDataRepository.findAllByValidToIsNull().map {
        ResearchDataInformation(
            researchDataId = it.researchDataEntity.id,
            fileName = it.name,
            description = it.description,
        )
      }

  fun uploadResearchData(
      keycloakId: String,
      dataConsumerId: UUID?,
      fileName: String,
      description: String,
      file: ByteArray,
  ): EntityInformation {
    val hubResearchData =
        hubResearchDataRepository.save(
            HubResearchDataEntity(
                userId = dataConsumerId,
            ),
        )
    satResearchDataMetaDataRepository.save(
        SatResearchDataMetaDataEntity(
            researchDataEntity = hubResearchData,
            name = fileName,
            description = description,
            file = file,
            validFrom = LocalDateTime.now(),
            validTo = null,
            modifyingUserId = UUID.fromString(keycloakId),
            status = ApprovalStatus.APPROVED.toString(),
        ),
    )
    return EntityInformation(id = hubResearchData.id, name = fileName)
  }
}
